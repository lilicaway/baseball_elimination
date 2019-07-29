# Baseball elimination algorithm: A maxflow formulation

This an assignment for the coursera course called [Algorithms part2](https://www.coursera.org/learn/algorithms-part2) from Princeton University.

In the baseball elimination problem, there is a division consisting of n teams. At some point during the season, team i has w[i] wins, l[i] losses, r[i] remaining games, and g[i][j] games left to play against team j.

A team is mathematically eliminated if it cannot possibly finish the season in (or tied for) first place. The goal is to determine exactly which teams are mathematically eliminated. For simplicity, we assume that no games end in a tie (as is the case in Major League Baseball) and that there are no rainouts (i.e., every scheduled game is played).

We now solve the baseball elimination problem by reducing it to the [maxflow problem](https://en.wikipedia.org/wiki/Maximum_flow_problem). 

The complete description of the assigments is [here](https://coursera.cs.princeton.edu/algs4/assignments/baseball/specification.php)
