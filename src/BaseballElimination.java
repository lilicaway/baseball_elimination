import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {

  private final Map<String, Integer> teamsToId;
  private final Map<Integer, String> idToTeams;
  private final int[] wins;
  private final int[] looses;
  private final int[] remaining;
  private final int[][] games;
  private final boolean[] eliminated;
  private final Map<Integer, Set<String>> certificates;


  /**
   * Create a baseball division from given filename in format specified below
   *
   * @param filename
   */
  public BaseballElimination(String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("Filename is null");
    }

    In in = new In(filename);
    int amountTeams = in.readInt();
    // Initialize variables
    teamsToId = new HashMap<>();
    idToTeams = new HashMap<>();
    wins = new int[amountTeams];
    looses = new int[amountTeams];
    remaining = new int[amountTeams];
    games = new int[amountTeams][amountTeams];
    certificates = new HashMap<>();

    for (int i = 0; i < amountTeams; i++) {
      String teamName = in.readString();
      teamsToId.put(teamName, i);
      idToTeams.put(i, teamName);
      certificates.put(i, new TreeSet<>());
      wins[i] = in.readInt();
      looses[i] = in.readInt();
      remaining[i] = in.readInt();
      for (int j = 0; j < amountTeams; j++) {
        games[i][j] = in.readInt();
      }
    }
    eliminated = new boolean[amountTeams];

    trivialElimination();
    nonTrivialElimination();

  }

  private void trivialElimination() {
    for (int x = 0; x < wins.length; x++) {
      int maxWinsX = wins[x] + remaining[x];
      for (int i = 0; i < wins.length; i++) {
        if (x == i) {
          assert (games[x][i] == 0);
          continue;
        }
        if (maxWinsX < wins[i]) {
          eliminated[x] = true;
          certificates.get(x).add(idToTeams.get(i));
        }
      }
    }
  }

  private void nonTrivialElimination() {
    int numberOfTeams = wins.length;

    for (int targetTeamId = 0; targetTeamId < numberOfTeams; targetTeamId++) {
      // We avoid computing the ones trivialy eliminated
      if (eliminated[targetTeamId]) {
        continue;
      }

      // We start using their API
      VertexMapper vm = new VertexMapper(numberOfTeams, targetTeamId);
      FlowNetwork fn = createFlowNetwork(vm);
      FordFulkerson ff = new FordFulkerson(fn, vm.getSourceVertex(), vm.getTargetVertex());
      verifyIfTeamIsEliminated(fn, vm);

      // Build certificates
      for (int i = 0; i < numberOfTeams; i++) {
        if (i == targetTeamId) {
          continue;
        }
        if (ff.inCut(vm.getTeamVertexForTeam(i))) {
          certificates.get(targetTeamId).add(idToTeams.get(i));
        }
      }
    }
  }

  private void verifyIfTeamIsEliminated(FlowNetwork fn, VertexMapper vm) {
    int sourceVertex = vm.getSourceVertex();
    boolean hasChances = true;
    for (FlowEdge edgeToGames : fn.adj(sourceVertex)) {
      if (!isFull(edgeToGames)) {
        hasChances = false;
      }
    }
    eliminated[vm.getTargetTeamId()] = !hasChances;
  }

  private boolean isFull(FlowEdge edgeToGames) {
    return Math.abs(edgeToGames.capacity() - edgeToGames.flow()) < 0.00001;
  }



  private FlowNetwork createFlowNetwork(VertexMapper vm) {
    int numberOfTeams = vm.getNumberOfTeams();
    int targetTeamId = vm.getTargetTeamId();
   
    FlowNetwork fn = new FlowNetwork(vm.getNumberOfVertices());

    for (int i = 0; i < numberOfTeams; i++) {
      if (i == targetTeamId) {
        continue;
      }
      fn.addEdge(new FlowEdge(vm.getTeamVertexForTeam(i), vm.getTargetVertex(),
          wins[targetTeamId] + remaining[targetTeamId] - wins[i]));
      for (int j = i + 1; j < numberOfTeams; j++) {
        if (j == targetTeamId) {
          continue;
        }
        fn.addEdge(
            new FlowEdge(vm.getSourceVertex(), vm.getTeamVsTeamVertexFor(i, j), games[i][j]));
        fn.addEdge(new FlowEdge(vm.getTeamVsTeamVertexFor(i, j), vm.getTeamVertexForTeam(i),
            Double.POSITIVE_INFINITY));
        fn.addEdge(new FlowEdge(vm.getTeamVsTeamVertexFor(i, j), vm.getTeamVertexForTeam(j),
            Double.POSITIVE_INFINITY));
      }
    }

    return fn;
  }

  // number of teams
  public int numberOfTeams() {
    return teamsToId.size();
  }

  // all teams
  public Iterable<String> teams() {
    return new ArrayList<>(teamsToId.keySet());
  }

  // number of wins for given team
  public int wins(String team) {
    if (team == null) {
      throw new IllegalArgumentException("Team arg cannot be null");
    }
    int id = getTeamId(team);

    return wins[id];
  }

  private int getTeamId(String team) {
    if (teamsToId.get(team) == null) {
      throw new IllegalArgumentException("Team requested doesn't exist");
    }
    return teamsToId.get(team);
  }

  // number of losses for given team
  public int losses(String team) {
    if (team == null) {
      throw new IllegalArgumentException("Team arg cannot be null");
    }
    int id = getTeamId(team);

    return looses[id];
  }

  // number of remaining games for given team
  public int remaining(String team) {
    if (team == null) {
      throw new IllegalArgumentException("Team arg cannot be null");
    }
    int id = getTeamId(team);

    return remaining[id];
  }

  // number of remaining games between team1 and team2
  public int against(String team1, String team2) {
    if (team1 == null || team2 == null) {
      throw new IllegalArgumentException("Team arg cannot be null");
    }
    int id1 = getTeamId(team1);
    int id2 = getTeamId(team2);
    return games[id1][id2];
  }

  // is given team eliminated?
  public boolean isEliminated(String team) {
    if (team == null) {
      throw new IllegalArgumentException("Team arg cannot be null");
    }
    int id = getTeamId(team);
    return eliminated[id];
  }

  // subset R of teams that eliminates given team; null if not eliminated
  public Iterable<String> certificateOfElimination(String team) {
    if (team == null || !teamsToId.containsKey(team)) {
      throw new IllegalArgumentException("Team arg cannot be null");
    }
    Set<String> certs = certificates.get(teamsToId.get(team));
    if (certs.isEmpty()) {
      return null;
    }
    return certs;
  }

  public static void main(String[] args) {
    BaseballElimination division = new BaseballElimination(args[0]);
    for (String team : division.teams()) {
      if (division.isEliminated(team)) {
        StdOut.print(team + " is eliminated by the subset R = { ");
        for (String t : division.certificateOfElimination(team)) {
          StdOut.print(t + " ");
        }
        StdOut.println("}");
      } else {
        StdOut.println(team + " is not eliminated");
      }
    }
  }

}
