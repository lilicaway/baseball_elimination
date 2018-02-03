
public class VertexMapper {
  private final int sId;
  private final int tId;
  private final int numberOfTeams;
  private final int[] teamVertexIds;
  private final int[][] gamesVertexIds;
  private final int targetTeamId;
  private final int numberOfVertices;

  public VertexMapper(int numberOfTeams, int targetTeamId) {
    if (targetTeamId < 0 || targetTeamId >= numberOfTeams) {
      throw new IllegalArgumentException("Invalid targetTeamId" + targetTeamId);
    }
    this.numberOfTeams = numberOfTeams;
    this.targetTeamId = targetTeamId;
    int nextIndexId = 0;
    teamVertexIds = new int[numberOfTeams];
    gamesVertexIds = new int[numberOfTeams][numberOfTeams];

    for (int i = 0; i < numberOfTeams; i++) {
      if (i == targetTeamId) {
        continue;
      }
      teamVertexIds[i] = nextIndexId++;
      for (int j = i + 1; j < numberOfTeams; j++) {
        if (j == targetTeamId) {
          continue;
        }
        gamesVertexIds[i][j] = nextIndexId++;
        gamesVertexIds[j][i] = gamesVertexIds[i][j];
      }
    }
    sId = nextIndexId++;
    tId = nextIndexId++;
    numberOfVertices = nextIndexId;
  }


  int getTeamVertexForTeam(int teamId) {
    checkTeamId(teamId);
    return teamVertexIds[teamId];

  }

  private void checkTeamId(int teamId) {
    if (teamId < 0 || teamId >= numberOfTeams) {
      throw new IllegalArgumentException("teamId:" + teamId);
    }
    if (teamId == targetTeamId) {
      throw new IllegalArgumentException(
          "teamId == targetTeamId that should not be in the graph: " + teamId);
    }
  }

  public int getTeamVsTeamVertexFor(int teamId1, int teamId2) {
    checkTeamId(teamId1);
    checkTeamId(teamId2);
    if (teamId1 == teamId2) {
      throw new IllegalArgumentException("Team cannot play against itself");
    }
    return gamesVertexIds[teamId1][teamId2];
  }

  public int getSourceVertex() {
    return sId;
  }

  public int getTargetVertex() {
    return tId;
  }

  public int getNumberOfVertices() {
    return numberOfVertices;
  }

  public int getNumberOfTeams() {
    return numberOfTeams;
  }

  public int getTargetTeamId() {
    return targetTeamId;
  }

}