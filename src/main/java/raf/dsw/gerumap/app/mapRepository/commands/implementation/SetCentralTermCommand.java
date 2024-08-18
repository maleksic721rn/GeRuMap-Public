package raf.dsw.gerumap.app.mapRepository.commands.implementation;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import raf.dsw.gerumap.app.mapRepository.commands.AbstractCommand;
import raf.dsw.gerumap.app.mapRepository.implementation.Element;
import raf.dsw.gerumap.app.mapRepository.implementation.Link;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Term;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

@RequiredArgsConstructor
public class SetCentralTermCommand extends AbstractCommand {

    @NonNull private MindMap map;
    @NonNull private Term term;
    private Term lastCenter = null;

    @AllArgsConstructor
    private static class Move {
        Term term;
        Point2D.Double from, to;
    }
    private ArrayList<Move> moves;

    @AllArgsConstructor
    private static class CircleAllocation {
        Term t;
        double r, alphaFrom, alphaTo;
    }

    @Override
    public void redo() {
        lastCenter = map.getCentralTerm();
        if (lastCenter != null)
            lastCenter.setCentral(false);
        term.setCentral(true);
        if (moves == null) {
            moves = new ArrayList<>();
            Point polar_origin = term.getCenter();
            Set<Term> visited = new HashSet<>();
            Stack<CircleAllocation> todoStack = new Stack<>();
            Stack<CircleAllocation> nextTodo = new Stack<>();
            todoStack.push(new CircleAllocation(term, 0, 0, 2 * Math.PI));
            while (!todoStack.isEmpty()) {
                while (!todoStack.isEmpty()) {
                    CircleAllocation current = todoStack.pop();
                    visited.add(current.t);
                    Point2D.Double converted = polarToCartesian(polar_origin, current.r, (current.alphaFrom + current.alphaTo)/2);
                    moves.add(new Move(current.t,
                            new Point2D.Double(current.t.getPosition().x, current.t.getPosition().y),
                            new Point2D.Double(converted.x - current.t.getCenter().x + current.t.getPosition().x,
                                    converted.y - current.t.getCenter().y + current.t.getPosition().y)));
                    List<Term> terms = new ArrayList<>();
                    for (Element<?> e : current.t.getParent().getChildren())
                        if (e instanceof Link l) {
                            if (l.getFrom() == current.t) {
                                if (!visited.contains(l.getTo()))
                                    terms.add(l.getTo());
                            }
                            else if (l.getTo() == current.t) {
                                if (!visited.contains(l.getFrom()))
                                    terms.add(l.getFrom());
                            }
                        }
                    if (terms.isEmpty())
                        continue;
                    double delta = (current.alphaTo - current.alphaFrom) / terms.size();
                    double from = current.alphaFrom;
                    double r = terms.stream().map(term1 -> Math.sqrt(
                            term1.getDimension().height * term1.getDimension().height +
                            term1.getDimension().width * term1.getDimension().width)).max(Double::compareTo).get()
                            + current.r
                            + Math.sqrt(
                            current.t.getDimension().height * current.t.getDimension().height +
                                    current.t.getDimension().width * current.t.getDimension().width);
                    for (Term t : terms) {
                        double to = from + delta;
                        nextTodo.push(new CircleAllocation(t, r, from, to));
                        from = to;
                    }
                }
                double r = nextTodo.stream().map(circleAllocation -> circleAllocation.r).max(Double::compareTo).orElse(0.0);
                for (CircleAllocation a : nextTodo)
                    a.r = r;
                Stack<CircleAllocation> tmp = todoStack;
                todoStack = nextTodo;
                nextTodo = tmp;
            }
        }
        for (Move m : moves)
            m.term.setPosition((int) m.to.x, (int) m.to.y);
    }

    @Override
    public void undo() {
        term.setCentral(false);
        if (lastCenter != null)
            lastCenter.setCentral(true);
        for (Move m : moves)
            m.term.setPosition((int) m.from.x, (int) m.from.y);
    }

    private static Point2D.Double polarToCartesian(Point2D origin, double r, double alpha) {
        return new Point2D.Double(origin.getX() + r * Math.cos(alpha), origin.getY() + r * Math.sin(alpha));
    }
}
