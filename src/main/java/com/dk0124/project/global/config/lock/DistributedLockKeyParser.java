package com.dk0124.project.global.config.lock;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class DistributedLockKeyParser {
    private DistributedLockKeyParser() {
    }

    public static String getDynamicValue(String[] parameterNames, Object[] args, String key) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        for (int i = 0; i < parameterNames.length; i++)
            context.setVariable(parameterNames[i], args[i]);

        String value = (String) parser.parseExpression(key).getValue(context, Object.class);
        return value;
    }
}