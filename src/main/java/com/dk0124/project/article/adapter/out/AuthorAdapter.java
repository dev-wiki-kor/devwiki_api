package com.dk0124.project.article.adapter.out;

import com.dk0124.project.article.application.port.out.AuthorPort;
import com.dk0124.project.article.domain.Author;
import com.dk0124.project.user.adapter.out.user.repository.UserGithubInfoEntityRepository;
import com.dk0124.project.user.adapter.out.user.repository.UserProfileEntityRepository;
import com.dk0124.project.user.exception.UserNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorAdapter implements AuthorPort {

    private final UserGithubInfoEntityRepository userGithubInfoEntityRepository;
    private final UserProfileEntityRepository userProfileEntityRepository;

    @Override
    public Author get(Long authorId) {

        var profile = userProfileEntityRepository.findByUserMetaId(authorId)
                .orElseThrow(() -> new UserNotExistException());

        var githubInfo = userGithubInfoEntityRepository.findByUserMetaId(authorId)
                .orElseThrow(() -> new UserNotExistException());

        return Author.of(profile.getUserMetaId(), profile.getNickname(), githubInfo.getGithubUrl());
    }
}
