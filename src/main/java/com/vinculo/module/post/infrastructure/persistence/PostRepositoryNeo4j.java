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
public interface PostRepositoryNeo4j extends Neo4jRepository<Post, Long> {

    @Query("""
            MATCH (me:person) WHERE id(me) = $authorId
            MATCH (me)-[connections:CONNECTED_WITH*1..2]-(author:person)-[rel:POSTED]->(post:post)
            WHERE author <> me
            
            WITH post, author, rel,
                 reduce(totalWeight = 0, r IN connections | totalWeight + r.weight) AS pathWeight
            
            RETURN post, rel, author
            ORDER BY pathWeight ASC, post.createdAt DESC
            SKIP $skip LIMIT $limit
            """)
    List<Post> findNetworkFeed(
            @Param("authorId") Long authorId,
            @Param("limit") int limit,
            @Param("skip") int skip
    );

    Page<Post> findAllByAuthorId(Long authorId, Pageable pageable);
}