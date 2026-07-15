package org.inspector.core.analyzers.controllerAnalyzer;

import com.github.javaparser.ast.expr.*;

import java.util.Optional;

public class ValidateAnnotationUtil {

    public static String getValueByAnnotation (String value) {
        if (annotationExpr.isMarkerAnnotationExpr()) {
            MarkerAnnotationExpr markerAnnotationExpr = annotationExpr.asMarkerAnnotationExpr();
            return null;
        } else if (annotationExpr.isSingleMemberAnnotationExpr()) {
            SingleMemberAnnotationExpr singleMemberAnnotationExpr = annotationExpr.asSingleMemberAnnotationExpr();
            return singleMemberAnnotationExpr.getMemberValue().toString();
        } else if (annotationExpr.isNormalAnnotationExpr()) {
            NormalAnnotationExpr normalAnnotationExpr = annotationExpr.asNormalAnnotationExpr();
            Optional<MemberValuePair> pair = normalAnnotationExpr.getPairs()
                    .stream()
                    .filter(memberValuePair -> memberValuePair.getNameAsString().equals(value))
                    .findFirst();

            return pair.map(memberValuePair -> memberValuePair.getValue().toString()).orElse(null);
        }

        return null;
    }
}
