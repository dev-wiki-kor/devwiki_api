package com.dk0124.project.common.user.join;

import com.dk0124.project.user.adapter.out.user.GithubJoinAdapter;
import com.dk0124.project.user.domain.JoinCommand;
import com.dk0124.project.user.adapter.out.user.entity.UserGithubInfoEntity;
import com.dk0124.project.user.adapter.out.user.entity.UserMetaEntity;
import com.dk0124.project.user.adapter.out.user.entity.UserProfileEntity;
import com.dk0124.project.user.adapter.out.user.repository.UserGithubInfoEntityRepository;
import com.dk0124.project.user.adapter.out.user.repository.UserMetaEntityRepository;
import com.dk0124.project.user.adapter.out.user.repository.UserProfileEntityRepository;
import com.dk0124.project.user.domain.UserRole;
import com.dk0124.project.user.domain.UserStatus;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class GithubJoinAdapterTest {


    static final FixtureMonkey monkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .build();

    @Mock
    private UserGithubInfoEntityRepository userGithubInfoEntityRepository;

    @Mock
    private UserProfileEntityRepository userProfileEntityRepository;

    @Mock
    private UserMetaEntityRepository userMetaEntityRepository;

    @InjectMocks
    private GithubJoinAdapter githubJoinAdapter;

    @Test
    void isUniqueIdAvailable_성공() {
        String uniqueId = "uniqueId123";
        when(userGithubInfoEntityRepository.existsByGithubUniqueId(uniqueId)).thenReturn(false);

        assertTrue(githubJoinAdapter.isUniqueIdAvailable(uniqueId));
    }

    @Test
    void isUniqueIdAvailable_실패() {
        String uniqueId = "uniqueId123";
        when(userGithubInfoEntityRepository.existsByGithubUniqueId(uniqueId)).thenReturn(true);

        assertFalse(githubJoinAdapter.isUniqueIdAvailable(uniqueId));
    }

    @Test
    void isNickNameAvailable_성공() {
        String nickname = "nickname123";
        when(userProfileEntityRepository.existsByNickname(nickname)).thenReturn(false);

        assertTrue(githubJoinAdapter.isNickNameAvailable(nickname));
    }

    @Test
    void isNickNameAvailable_실패() {
        String nickname = "nickname123";
        when(userProfileEntityRepository.existsByNickname(nickname)).thenReturn(true);

        assertFalse(githubJoinAdapter.isNickNameAvailable(nickname));
    }

    @Test
    void join_성공() {
        JoinCommand joinCommand = new JoinCommand("uniqueId123", "email@example.com", "pageUrl", "profileUrl", "nickname123");

        UserMetaEntity savedEntity = monkey.giveMeBuilder(UserMetaEntity.class)
                .set("id",1L)
                .set("userRoles", Set.of(UserRole.USER))
                .set("userStatus", Set.of(UserStatus.NORMAL))
                .set("active", true)
                .sample();

        when(userMetaEntityRepository.save(any(UserMetaEntity.class)))
                .thenReturn(savedEntity);

        githubJoinAdapter.join(joinCommand);

        verify(userProfileEntityRepository).save(any(UserProfileEntity.class));
        verify(userGithubInfoEntityRepository).save(any(UserGithubInfoEntity.class));
    }
}
