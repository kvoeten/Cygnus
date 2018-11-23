/*
    This file is part of JWzLib: Universal MapleStory WZ File Parser
    Copyright (C) 2014  Zygon <watchmystarz@hotmail.com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package wz.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantLock;
import org.nfunk.jep.JEP;

/**
 *
 * @author Zygon
 */
public final class Evaluator {

    private static JEP eval = null;
    private static final ReentrantLock lock = new ReentrantLock(true);

    private Evaluator() {
    }

    public static double evaluate(String expression, Map<String, Double> variables) {
        lock.lock();
        try {
            if (eval == null) {
                eval = new JEP();
                eval.initFunTab();
                eval.addStandardFunctions();
                eval.setImplicitMul(true);
            }
            eval.initSymTab();
            eval.addStandardConstants();
            eval.addComplex();
            for (Entry<String, Double> variable : variables.entrySet()) {
                eval.addVariable(variable.getKey(), variable.getValue());
            }
            eval.parseExpression(expression.replaceAll("u", "ceil").replaceAll("d", "floor"));
            return eval.getValue();
        } finally {
            lock.unlock();
        }
    }
}
