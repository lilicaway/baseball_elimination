import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {

  Map<String, Integer> teams;
  int[] wins;
  int[] looses;
  int[] remaining;
  int[][] games;
  boolean[] isEliminated;
  // Not sure about this
  List<String> certificates;

  // create a baseball division from given filename in format specified below
  public BaseballElimination(String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("Filename is null");
    }

    In in = new In(filename);
    int amountTeams = in.readInt();
    // Initialize variables
    teams = new HashMap<>();
    wins = new int[amountTeams];
    looses = new int[amountTeams];
    remaining = new int[amountTeams];
    games = new int[amountTeams][amountTeams];

    for (int i = 0; i < amountTeams; i++) {
      teams.put(in.readString(), i);
      wins[i] = in.readInt();
      looses[i] = in.readInt();
      remaining[i] = in.readInt();
      for (int j = 0; j < amountTeams; j++) {
        games[i][j] = in.readInt();
      }
    }
    isEliminated = new boolean[amountTeams];
    certificates = new ArrayList<>();

    trivialElimination();
    nonTrivialElimination();

  }

  private void nonTrivialElimination() {
    // TODO Auto-generated method stub

  }

  private void trivialElimination() {
    // TODO Auto-generated method stub

  }

  // number of teams
  public int numberOfTeams() {
    return teams.size();
  }

  // all teams
  public Iterable<String> teams() {
    return new ArrayList<>(teams.keySet());
  }

  // number of wins for given team
  public int wins(String team) {
    if (team == null) {
      throw new IllegalArgumentException("Team arg cannot be null");
    }
    return 0;
  }

  // number of losses for given team
  public int losses(String team) {
    if (team == null) {
      throw new IllegalArgumentException("Team arg cannot be null");
    }
    return 0;
  }

  // number of remaining games for given team
  public int remaining(String team) {
    if (team == null) {
      throw new IllegalArgumentException("Team arg cannot be null");
    }
    return 0;
  }

  // number of remaining games between team1 and team2
  public int against(String team1, String team2) {
    if (team1 == null || team2 == null) {
      throw new IllegalArgumentException("Team arg cannot be null");
    }
    return 0;
  }

  // is given team eliminated?
  public boolean isEliminated(String team) {
    if (team == null) {
      throw new IllegalArgumentException("Team arg cannot be null");
    }
    return false;
  }

  // subset R of teams that eliminates given team; null if not eliminated
  public Iterable<String> certificateOfElimination(String team) {
    if (team == null) {
      throw new IllegalArgumentException("Team arg cannot be null");
    }
    return null;
  }

  public static void main(String[] args) {
    BaseballElimination division = new BaseballElimination(args[0]);
    for (String team : division.teams()) {
      StdOut.print(team + " ");
      // if (division.isEliminated(team)) {
      // StdOut.print(team + " is eliminated by the subset R = { ");
      // for (String t : division.certificateOfElimination(team)) {
      // StdOut.print(t + " ");
      // }
      // StdOut.println("}");
      // } else {
      // StdOut.println(team + " is not eliminated");
      // }
    }
  }

}
