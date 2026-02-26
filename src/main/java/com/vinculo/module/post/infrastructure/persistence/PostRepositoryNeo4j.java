package com.vinculo.module.post.infrastructure.persistence;

import com.vinculo.module.post.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepositoryNeo4j extends Neo4jRepository<Post, String> {

    @Query(value = """
            MATCH (me:Person {id: $personId})-[c:CONNECTED_WITH]->(author:Person)
            MATCH (author)-[rel:POSTED]->(p:Post)
            RETURN p, rel, author
            ORDER BY c.weight ASC, p.createdAt DESC
            SKIP $skip LIMIT $limit
            """)
    List<Post> findNetworkFeed(
            @Param("personId") String personId,
            @Param("skip") long skip,
            @Param("limit") int limit
    );

    @Query(value = """
            MATCH (a:Person {id: $authorId})-[rel:POSTED]->(p:Post)
            RETURN p, rel, a
            ORDER BY p.createdAt DESC
            SKIP $skip LIMIT $limit
            """,
            countQuery = "MATCH (a:Person {id: $authorId})-[:POSTED]->(p:Post) RETURN count(p)")
    Page<Post> findAllByAuthorId(@Param("authorId") String authorId, @Param("skip") long skip, @Param("limit") int limit, Pageable pageable);

}