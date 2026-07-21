package org.inspector.core.analyzers.utils;

import com.github.javaparser.ast.expr.*;

import java.util.Optional;

public class ValidateAnnotationUtil {

    public static Optional<Expression> getValueByAnnotation (AnnotationExpr annotationExpr, String[] annotationValue) {
        if (annotationExpr.isMarkerAnnotationExpr()) {
            MarkerAnnotationExpr markerAnnotationExpr = annotationExpr.asMarkerAnnotationExpr();
            return Optional.empty();

        } else if (annotationExpr.isSingleMemberAnnotationExpr()) {
            SingleMemberAnnotationExpr singleMemberAnnotationExpr = annotationExpr.asSingleMemberAnnotationExpr();
            return Optional.of(singleMemberAnnotationExpr.getMemberValue());

        } else if (annotationExpr.isNormalAnnotationExpr()) {
            NormalAnnotationExpr normalAnnotationExpr = annotationExpr.asNormalAnnotationExpr();
            Optional<MemberValuePair> pair = normalAnnotationExpr.getPairs()
                    .stream()
                    .filter(memberValuePair -> {
                        for (String ann : annotationValue) {
                            if (memberValuePair.getNameAsString().equals(ann)) return true;
                        }
                        return false;
                    })
                    .findFirst();
            return pair.map(MemberValuePair::getValue);
        }

        return Optional.empty();
    }
}
