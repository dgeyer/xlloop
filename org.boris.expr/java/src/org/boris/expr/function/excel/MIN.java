package org.boris.expr.function.excel;

import org.boris.expr.Expr;
import org.boris.expr.ExprArray;
import org.boris.expr.ExprDouble;
import org.boris.expr.ExprError;
import org.boris.expr.ExprEvaluatable;
import org.boris.expr.ExprException;
import org.boris.expr.ExprNumber;
import org.boris.expr.function.AbstractFunction;

public class MIN extends AbstractFunction
{
    public Expr evaluate(Expr[] args) throws ExprException {
        assertMinArgCount(args, 1);
        return min(args);
    }

    public static Expr min(Expr[] args) throws ExprException {
        double d = Double.MAX_VALUE;
        for (Expr a : args) {
            Expr res = min(a);
            if (res instanceof ExprError) {
                return res;
            } else {
                double r = ((ExprDouble) res).doubleValue();
                if (r < d)
                    d = r;
            }
        }
        return new ExprDouble(d);
    }

    public static Expr min(Expr arg) throws ExprException {
        if (arg instanceof ExprEvaluatable) {
            arg = ((ExprEvaluatable) arg).evaluate();
        }

        if (arg instanceof ExprNumber) {
            return new ExprDouble(((ExprNumber) arg).doubleValue());
        }

        if (arg instanceof ExprArray) {
            return min(((ExprArray) arg).getArgs());
        }

        if (arg instanceof ExprError) {
            return arg;
        }

        return ExprError.VALUE;
    }
}
