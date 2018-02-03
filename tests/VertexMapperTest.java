import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class VertexMapperTest {
  private VertexMapper vertexMapper;

  @Before
  public void setup() {
    int numberOfTeams = 5;
    int targetTeamId = 3;
    vertexMapper = new VertexMapper(numberOfTeams, targetTeamId);
  }


  @Test
  public void testVertexMapper() {
    assertEquals(0, vertexMapper.getTeamVertexForTeam(0));

    assertEquals(1, vertexMapper.getTeamVsTeamVertexFor(0, 1));
    assertEquals(1, vertexMapper.getTeamVsTeamVertexFor(1, 0));

    assertEquals(2, vertexMapper.getTeamVsTeamVertexFor(0, 2));
    assertEquals(2, vertexMapper.getTeamVsTeamVertexFor(2, 0));

    assertEquals(3, vertexMapper.getTeamVsTeamVertexFor(0, 4));
    assertEquals(3, vertexMapper.getTeamVsTeamVertexFor(4, 0));

    assertEquals(4, vertexMapper.getTeamVertexForTeam(1));

    assertEquals(5, vertexMapper.getTeamVsTeamVertexFor(1, 2));
    assertEquals(5, vertexMapper.getTeamVsTeamVertexFor(2, 1));

    assertEquals(6, vertexMapper.getTeamVsTeamVertexFor(1, 4));
    assertEquals(6, vertexMapper.getTeamVsTeamVertexFor(4, 1));

    assertEquals(7, vertexMapper.getTeamVertexForTeam(2));

    assertEquals(8, vertexMapper.getTeamVsTeamVertexFor(2, 4));
    assertEquals(8, vertexMapper.getTeamVsTeamVertexFor(4, 2));

    assertEquals(9, vertexMapper.getTeamVertexForTeam(4));
    assertEquals(10, vertexMapper.getSourceVertex());
    assertEquals(11, vertexMapper.getTargetVertex());

    assertEquals(12, vertexMapper.getNumberOfVertices());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFailsOnTargetTeam() {
    assertEquals(3, vertexMapper.getTeamVertexForTeam(3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFailsOnTargetNameInGame() {
    vertexMapper.getTeamVsTeamVertexFor(0, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFailsOnTargetNameInGameReversed() {
    vertexMapper.getTeamVsTeamVertexFor(3, 0);
  }
}
