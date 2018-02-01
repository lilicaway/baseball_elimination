import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Test;

public class BaseballEliminationTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorArgumentIsNull() {
    new BaseballElimination(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWinsWithNullArgument() throws Exception {
    BaseballElimination be = new BaseballElimination("data/teams1.txt");
    be.wins(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLossesWithNullArgument() throws Exception {
    BaseballElimination be = new BaseballElimination("data/teams1.txt");
    be.losses(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemainingWithNullArgument() throws Exception {
    BaseballElimination be = new BaseballElimination("data/teams1.txt");
    be.remaining(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsEliminatedWithNullArgument() throws Exception {
    BaseballElimination be = new BaseballElimination("data/teams1.txt");
    be.isEliminated(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCertificateOEWithNullArgument() throws Exception {
    BaseballElimination be = new BaseballElimination("data/teams1.txt");
    be.certificateOfElimination(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAgainstWithNullArgment() throws Exception {
    BaseballElimination be = new BaseballElimination("data/teams1.txt");
    be.against(null, "Any Name");
  }

  // Checks creates and reads structures correctly

  @Test
  public void testTeamsAndDataIsLoaded() throws Exception {
    BaseballElimination be = new BaseballElimination("data/teams10.txt");

    assertEquals(10, be.teams.length);
    assertEquals(10, be.wins.length);
    assertEquals(10, be.looses.length);
    assertEquals(10, be.remaining.length);

    // Actually check on some data
    assertEquals(63, be.remaining[0]);
    assertEquals(31, be.wins[8]);
    assertEquals(31, be.looses[9]);
    assertEquals("Boston", be.teams[1]);

    // Assert some of the games data
    // Boston - Indiana 0
    assertEquals(0, be.games[1][9]);
    // Denver - Atlanta 9
    assertEquals(9, be.games[5][0]);
  }

  // How do I test inmutability here?
  // @Test
  // public void testInmutableTeams() throws Exception {
  // BaseballElimination be = new BaseballElimination("data/teams10.txt");
  //
  // Iterable<String> te = be.teams();
  //
  // assertNotNull(be.teams()); te = null;
  // }

  @Test
  public void testTeamsAre10() throws Exception {
    BaseballElimination be = new BaseballElimination("data/teams10.txt");

    int count = 0;
    Iterator<String> it = be.teams().iterator();
    while (it.hasNext()) {
      count++;
      it.next();
    }
    assertEquals(10, count);
  }

}
