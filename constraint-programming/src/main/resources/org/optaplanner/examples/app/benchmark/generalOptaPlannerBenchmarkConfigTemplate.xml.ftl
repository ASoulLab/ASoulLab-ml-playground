<?xml version="1.0" encoding="UTF-8"?>
<plannerBenchmark>
  <benchmarkDirectory>local/data/general/template</benchmarkDirectory>
  <warmUpSecondsSpentLimit>30</warmUpSecondsSpentLimit>

  <inheritedSolverBenchmark>
    <problemBenchmarks>
      <problemStatisticType>BEST_SCORE</problemStatisticType>
    </problemBenchmarks>
    <solver>
      <termination>
        <minutesSpentLimit>5</minutesSpentLimit>
      </termination>
    </solver>
  </inheritedSolverBenchmark>


<#list ['JDK', 'MERSENNE_TWISTER', 'WELL512A', 'WELL1024A', 'WELL19937C', 'WELL44497B'] as randomType>
  <solverBenchmark>
    <name>Cloud Balancing Late Acceptance ${randomType}</name>
    <problemBenchmarks>
      <xStreamAnnotatedClass>org.optaplanner.examples.cloudbalancing.domain.CloudBalance</xStreamAnnotatedClass>
      <inputSolutionFile>data/cloudbalancing/unsolved/200computers-600processes.xml</inputSolutionFile>
      <inputSolutionFile>data/cloudbalancing/unsolved/800computers-2400processes.xml</inputSolutionFile>
    </problemBenchmarks>
    <solver>
      <solutionClass>org.optaplanner.examples.cloudbalancing.domain.CloudBalance</solutionClass>
      <entityClass>org.optaplanner.examples.cloudbalancing.domain.CloudProcess</entityClass>
      <randomType>${randomType}</randomType>
      <scoreDirectorFactory>
        <scoreDefinitionType>HARD_SOFT</scoreDefinitionType>
        <scoreDrl>org/optaplanner/examples/cloudbalancing/solver/cloudBalancingScoreRules.drl</scoreDrl>
        <initializingScoreTrend>ONLY_DOWN</initializingScoreTrend>
      </scoreDirectorFactory>
      <constructionHeuristic>
        <constructionHeuristicType>FIRST_FIT_DECREASING</constructionHeuristicType>
      </constructionHeuristic>
      <localSearch>
        <unionMoveSelector>
          <c