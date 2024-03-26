package pl.auroramc.messages.placeholder;

import static java.util.Comparator.comparing;
import static java.util.Locale.ROOT;
import static javax.lang.model.SourceVersion.RELEASE_17;
import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.ElementKind.FIELD;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;
import static pl.auroramc.messages.placeholder.scanner.PlaceholderScanner.getPlaceholderScanner;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import pl.auroramc.messages.placeholder.scanner.PlaceholderScanner;

@SupportedAnnotationTypes("pl.auroramc.messages.placeholder.GeneratePaths")
@SupportedSourceVersion(RELEASE_17)
@AutoService(Processor.class)
public class PathsGenerationProcessor extends AbstractProcessor {

  private final PlaceholderScanner placeholderScanner = getPlaceholderScanner();
  private final Set<String> generatedPaths = new HashSet<>();

  @Override
  public boolean process(
      final Set<? extends TypeElement> annotations, final RoundEnvironment environment) {
    for (final TypeElement annotation : annotations) {
      final Set<? extends Element> elements = environment.getElementsAnnotatedWith(annotation);
      for (final Element element : elements) {
        if (element.getKind() == CLASS) {
          generateClass((TypeElement) element);
        }
      }
    }
    return false;
  }

  private void generateClass(final TypeElement typeElement) {
    final Builder builder =
        TypeSpec.classBuilder(typeElement.getSimpleName().toString() + "Paths")
            .addModifiers(PUBLIC, FINAL);

    final List<FieldSpec> fieldSpecs = new ArrayList<>();
    final List<? extends Element> enclosedElements = typeElement.getEnclosedElements();
    for (final Element enclosedElement : enclosedElements) {
      if (enclosedElement.getKind() == FIELD) {
        fieldSpecs.addAll(generateFields((VariableElement) enclosedElement));
      }
    }

    if (fieldSpecs.isEmpty()) {
      return;
    }

    fieldSpecs.stream().sorted(comparing(spec -> spec.name)).forEach(builder::addField);
    saveGeneratedClass(builder.build(), typeElement);
  }

  private void saveGeneratedClass(final TypeSpec typeSpec, final TypeElement element) {
    try {
      JavaFile.builder(
              processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString(),
              typeSpec)
          .build()
          .writeTo(processingEnv.getFiler());
    } catch (final IOException exception) {
      throw new PathsGeneratingException(
          "Could not generate paths class, because of unexpected exception.", exception);
    }
  }

  private List<FieldSpec> generateFields(final VariableElement element) {
    final Object constantValue = element.getConstantValue();
    if (constantValue instanceof String message) {
      final List<String> paths =
          new CopyOnWriteArrayList<>(List.of(placeholderScanner.getPlaceholderPaths(message)));
      if (paths.isEmpty()) {
        return List.of();
      }

      for (final String path : paths) {
        if (path.contains(".")) {
          paths.add(path.substring(0, path.indexOf('.')));
        }
      }

      return paths.stream().map(this::generateField).filter(Objects::nonNull).toList();
    }

    return List.of();
  }

  private FieldSpec generateField(final String path) {
    if (generatedPaths.contains(path)) {
      return null;
    }

    generatedPaths.add(path);
    return FieldSpec.builder(String.class, getNormalizedPath(path))
        .addModifiers(PUBLIC, STATIC, FINAL)
        .initializer("$S", path)
        .build();
  }

  private String getNormalizedPath(final String path) {
    return path.replace(".", "_").toUpperCase(ROOT) + "_PATH";
  }
}
