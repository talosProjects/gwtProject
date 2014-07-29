package com.mozido.channels.generator;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.mozido.channels.nextweb.App;
import com.mozido.channels.nextweb.CmpSupportsReflection;
import com.mozido.channels.nextweb.access.FunctionalGroup;
import com.mozido.channels.nextweb.history.HistoryToken;
import com.mozido.channels.nextweb.history.SplitCode;
import com.mozido.channels.nextweb.model.ui.FGroup;
import com.mozido.channels.nextweb.MzReflectionService;
import com.mozido.channels.nextweb.utils.StringUtils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Generator for Reflection functional
 *
 * @author Alexander Manusovich
 */
public class ReflectionGenerator extends Generator {

    /**
     * Generator for method which is making components
     *
     * @param clazzes List of classes
     * @param writer  Writer for generator
     */
    private void methodInstatiate(List<JClassType> clazzes, SourceWriter writer) {
        writer.println();
        writer.println("public <T extends MzComponent> void instantiate(final String className,"
                + " final OnComponentReadyAction<T> onLoadAction) {");
        for (JClassType classType : clazzes) {
            if (classType.isAbstract())
                continue;
            writer.println();
            writer.indent();
            String className = classType.getQualifiedSourceName();
            writer.println("if (className.equalsIgnoreCase(\""
                    + className + "\")) {");
            writer.indent();

            if (classType.isAnnotationPresent(SplitCode.class)) {
                writer.println("GWT.runAsync(" + className + ".class, new RunAsyncCallback() {");
                writer.indent();
                writer.println("@Override");
                writer.println("public void onFailure(Throwable throwable) {");
                writer.indent();
                writer.println("MzNotificationManager.displayError(\"Failed to download code for widget "
                        + className + "\");");
                writer.println("}");
                writer.outdent();
                writer.println();
                writer.println("@Override");
                writer.println("public void onSuccess() {");
                writer.indent();
                writer.println("onLoadAction.onReady((T) new " + className + "()); ");
                writer.outdent();
                writer.println("}");
                writer.outdent();
                writer.println("});");
            } else {
                writer.println("onLoadAction.onReady((T) new " + className + "()); ");
            }

            writer.outdent();
            writer.println("}");
            writer.outdent();
            writer.println();
        }
        writer.indent();
        writer.println();
        writer.println("}");
        writer.outdent();
        writer.println();
    }

    /**
     * Generator for method which is provide history token by class name. This method
     * checking all HistoryToken annotations.
     *
     * @param clazzes List of classes
     * @param writer  Writer for generator
     */
    private void methodGetTokenByClass(List<JClassType> clazzes, SourceWriter writer) {
        writer.println();
        writer.println("public String getHistoryTokenByClass(final Class clazz) {");
        for (JClassType classType : clazzes) {
            if (classType.isAbstract())
                continue;
            String token = null;
            if (classType.isAnnotationPresent(HistoryToken.class)) {
                token = classType.getAnnotation(HistoryToken.class).value();
            }
            if (token == null || token.isEmpty()) {
                token = classType.getQualifiedSourceName();
            }
            writer.println();
            writer.indent();
            writer.println("if (clazz.getName().equalsIgnoreCase(\""
                    + classType.getQualifiedSourceName() + "\")) {");
            writer.indent();
            writer.println("return \"" + token + "\";");
            writer.outdent();
            writer.println("}");
            writer.outdent();
            writer.println();
        }
        writer.indent();
        writer.println("return null;");
        writer.outdent();
        writer.println();
        writer.println("}");
        writer.outdent();
        writer.println();
    }

    /**
     * Generator for method which is provide functional group by class name.
     *
     * @param clazzes List of classes
     * @param writer  Writer for generator
     */
    private void methodGetFunctionalGroupByClass(List<JClassType> clazzes, SourceWriter writer) {
        writer.println();
        writer.println("public FunctionalGroupData getFunctionalGroupByClass(final Class clazz) {");
        for (JClassType classType : clazzes) {
            if (classType.isAbstract())
                continue;
            writer.println();
            writer.indent();
            writer.println("if (clazz.getName().equalsIgnoreCase(\""
                    + classType.getQualifiedSourceName() + "\")) {");
            writer.indent();
            if (classType.isAnnotationPresent(FunctionalGroup.class)) {
                FGroup[] value = classType.getAnnotation(FunctionalGroup.class).value();
                String[] valueStr = new String[value.length];
                for (int i = 0; i < value.length; i++) {
                    valueStr[i] = "FGroup." + value[i].toString();
                }

                Class ifIsNotAllowedUseInstead = classType
                        .getAnnotation(FunctionalGroup.class).ifIsNotAllowedUseInstead();
                String ifIsNotAllowedUseInsteadStr = "null";
                if (ifIsNotAllowedUseInstead != null && !ifIsNotAllowedUseInstead.equals(Object.class)) {
                    ifIsNotAllowedUseInsteadStr = ifIsNotAllowedUseInstead.getName() + ".class";
                }
                String newInstStr = "new FunctionalGroupData(new FGroup[]{" + StringUtils.join(valueStr, ",") + "}, "
                        + ifIsNotAllowedUseInsteadStr + ")";
                writer.println("return " + newInstStr + ";");
            }
            writer.outdent();
            writer.println("}");
            writer.outdent();
            writer.println();
        }
        writer.indent();
        writer.println("return null;");
        writer.outdent();
        writer.println();
        writer.println("}");
        writer.outdent();
        writer.println();
    }

    /**
     * Generator for method which is provide class for component by this history token.
     *
     * @param clazzes List of classes
     * @param writer  Writer for generator
     */
    private void methodGetClassByToken(List<JClassType> clazzes, SourceWriter writer) {
        writer.println();
        writer.println("public Class getClassByHistoryToken(final String token) {");
        for (JClassType classType : clazzes) {
            if (classType.isAbstract())
                continue;
            String token = null;
            if (classType.isAnnotationPresent(HistoryToken.class)) {
                token = classType.getAnnotation(HistoryToken.class).value();
            }
            if (token == null || token.isEmpty()) {
                token = classType.getQualifiedSourceName();
            }
            writer.println();
            writer.indent();
            writer.println("if (token.equalsIgnoreCase(\""
                    + token + "\")) {");
            writer.indent();
            writer.println("return " + classType.getQualifiedSourceName() + ".class;");
            writer.outdent();
            writer.println("}");
            writer.outdent();
            writer.println();
        }
        writer.indent();
        writer.println("return null;");
        writer.outdent();
        writer.println();
        writer.println("}");
        writer.outdent();
        writer.println();
    }

    @Override
    public String generate(final TreeLogger logger,
                           final GeneratorContext context,
                           final String typeName) throws UnableToCompleteException {
        TypeOracle oracle = context.getTypeOracle();
        JClassType instantiableType = oracle.findType(CmpSupportsReflection.class.getName());

        List<JClassType> clazzes = new ArrayList<JClassType>();
        for (JClassType classType : oracle.getTypes()) {
            if (!classType.equals(instantiableType) && classType.isAssignableTo(instantiableType))
                clazzes.add(classType);
        }

        final String genPackageName = App.PACKAGE_ENTRY_POINT;
        final String genClassName = "ReflectionImpl";

        ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(
                genPackageName, genClassName);
        composer.addImplementedInterface(MzReflectionService.class.getCanonicalName());

        composer.addImport(genPackageName + ".*");
        composer.addImport(genPackageName + ".errors.*");
        composer.addImport(genPackageName + ".access.*");
        composer.addImport(genPackageName + ".utils.*");
        composer.addImport(genPackageName + ".model.ui.*");
        composer.addImport("com.google.gwt.core.client.*");
        composer.addImport("com.google.gwt.user.client.ui.*");
        composer.addImport("com.google.gwt.user.client.rpc.*");

        PrintWriter printWriter = context.tryCreate(logger, genPackageName, genClassName);

        if (printWriter != null) {
            SourceWriter sourceWriter = composer.createSourceWriter(context, printWriter);
            sourceWriter.println("ReflectionImpl( ) {");
            sourceWriter.println("}");

            methodInstatiate(clazzes, sourceWriter);
            methodGetTokenByClass(clazzes, sourceWriter);
            methodGetClassByToken(clazzes, sourceWriter);
            methodGetFunctionalGroupByClass(clazzes, sourceWriter);

            sourceWriter.commit(logger);
        }
        return composer.getCreatedClassName();
    }


}