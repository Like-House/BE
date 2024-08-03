package backend.like_house.domain.chatting.service;

import backend.like_house.domain.chatting.entity.Chat;
import backend.like_house.domain.user.entity.SocialType;
import backend.like_house.global.socket.dto.ChattingDTO;

public interface ChatCommandService {

    Chat saveChat(ChattingDTO.MessageDTO messageDTO, String email, SocialType socialType);
}
