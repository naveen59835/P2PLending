package com.stackroute.recommendation.repository;

import com.stackroute.recommendation.domain.RecommendedBorrower;
//import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends Neo4jRepository<RecommendedBorrower,String> {



 @Query(" MATCH(c:cibil {score:$score})<-[:Has]-(b:Borrower) return b")
  List<RecommendedBorrower> getAllBorrowers(String score);





































//
//    @Query("MATCH (p:Person {name:$name})-[:FRIEND_OF]->(:Person)-[:INVESTED_IN]->(s:Stock) return s")
//    List<Stock> firstLevelRecommendation(String name);
//
//    @Query("MATCH (p:Person {name:$name})-[:FRIEND_OF]->(:Person)-[:FRIEND_OF]->(p2:Person)-[:INVESTED_IN]->(s:Stock) return s")
//
//
//


}
