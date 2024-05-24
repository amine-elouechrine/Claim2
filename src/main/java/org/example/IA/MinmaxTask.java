package org.example.IA;

import org.example.Modele.Plateau;

import java.util.Map;
import java.util.concurrent.RecursiveTask;

class MinimaxTask extends RecursiveTask<Integer> {
    private Node node;
    private int depth;
    private boolean maximizingPlayer;
    private int alpha;
    private int beta;
    private Map<Plateau, Integer> memo;

    public MinimaxTask(Node node, int depth, boolean maximizingPlayer, int alpha, int beta, Map<Plateau, Integer> memo) {
        this.node = node;
        this.depth = depth;
        this.maximizingPlayer = maximizingPlayer;
        this.alpha = alpha;
        this.beta = beta;
        this.memo = memo;
    }

    @Override
    protected Integer compute() {
        IAMinMax iam = new IAMinMax(memo);
        return iam.minimax(node, depth, maximizingPlayer, alpha, beta);
    }
}
