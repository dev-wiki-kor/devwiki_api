package com.dk0124.project.common.user;


import com.dk0124.project.auth.domain.UserRole;
import com.dk0124.project.common.QueryDslTestConfig;
import com.dk0124.project.common.user.adapter.out.user.UserExistCheckAdapter;
import com.dk0124.project.common.user.adapter.out.user.UserGithubInfo;
import com.dk0124.project.common.user.adapter.out.user.entity.UserGithubInfoEntity;
import com.dk0124.project.common.user.adapter.out.user.entity.UserMetaEntity;
import com.dk0124.project.common.user.adapter.out.user.repository.UserGithubInfoEntityRepository;
import com.dk0124.project.common.user.adapter.out.user.repository.UserMetaEntityRepository;
import com.dk0124.project.common.user.exception.UserNotExistException;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Set;

import static com.navercorp.fixturemonkey.api.experimental.JavaGetterMethodPropertySelector.javaGetter;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("local")
@Import({QueryDslTestConfig.class, UserExistCheckAdapter.class})
@Slf4j
@Transactional
public class UserExistCheckAdapterTest {

    @Autowired
    private UserExistCheckAdapter userExistCheckAdapter;

    @Autowired
    private UserMetaEntityRepository userMetaEntityRepository;

    @Autowired
    private UserGithubInfoEntityRepository userGithubInfoEntityRepository;


    @Test
    void 유저_존재_쿼리_성공() {
        // Given
        String GITHUB_UNIQUE_ID = "111";
        final FixtureMonkey monkey = FixtureMonkey.builder()
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .build();

        UserMetaEntity userMetaEntity = monkey.giveMeBuilder(UserMetaEntity.class)
                .set("userRoles", Set.of(UserRole.USER))
                .set("active", true)
                .sample();

        UserMetaEntity saved = userMetaEntityRepository.save(userMetaEntity);

        UserGithubInfoEntity userGithubInfoEntity = monkey.giveMeBuilder(UserGithubInfoEntity.class)
                .set(javaGetter(UserGithubInfoEntity::getUserMetaId), saved.getId())
                .set(javaGetter(UserGithubInfoEntity::getGithubUniqueId), GITHUB_UNIQUE_ID)
                .sample();

        userGithubInfoEntityRepository.save(userGithubInfoEntity);

        // When
        var  userGithubInfo =
                userExistCheckAdapter.findByGithubUniqueId(GITHUB_UNIQUE_ID);

        // Then
        assertNotNull(userGithubInfo);
        assertEquals( GITHUB_UNIQUE_ID, userGithubInfo.getGithubUniqueId() );
        assertEquals( 1, userGithubInfo.getUserRoles().size());
        assertTrue( userGithubInfo.getUserRoles().contains(UserRole.USER) );

    }

    @Test
    void 유저_존재_쿼리_실패_휴면_계정(){
        String GITHUB_UNIQUE_ID = "111";
        final FixtureMonkey monkey = FixtureMonkey.builder()
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .build();

        UserMetaEntity userMetaEntity = monkey.giveMeBuilder(UserMetaEntity.class)
                .set("userRoles", Set.of(UserRole.USER))
                .set("active", false)
                .sample();

        UserMetaEntity saved = userMetaEntityRepository.save(userMetaEntity);

        UserGithubInfoEntity userGithubInfoEntity = monkey.giveMeBuilder(UserGithubInfoEntity.class)
                .set(javaGetter(UserGithubInfoEntity::getUserMetaId), saved.getId())
                .set(javaGetter(UserGithubInfoEntity::getGithubUniqueId), GITHUB_UNIQUE_ID)
                .sample();

        userGithubInfoEntityRepository.save(userGithubInfoEntity);

        assertThrows(UserNotExistException.class, ()-> userExistCheckAdapter.findByGithubUniqueId(GITHUB_UNIQUE_ID));


    }
    @Test
    void 유저_없어서_쿼리_null_리턴() {
        assertThrows(UserNotExistException.class, ()-> userExistCheckAdapter.findByGithubUniqueId("xxx"));
    }
}
