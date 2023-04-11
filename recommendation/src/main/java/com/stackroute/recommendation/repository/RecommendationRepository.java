package com.stackroute.recommendation.repository;

import com.stackroute.recommendation.domain.RecommendedBorrower;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface RecommendationRepository extends Neo4jRepository<RecommendedBorrower,String> {



 @Query(" MATCH(c:cibil {score:$score})<-[:Has]-(b:Borrower) return b")
  List<RecommendedBorrower> getAllBorrowers(String score);

}
