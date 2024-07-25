package backend.like_house.domain.chatting.service;

import backend.like_house.global.socket.dto.ChattingDTO;

public interface ChatCommandService {

    void saveChat(ChattingDTO.MessageDTO messageDTO, String email);
}
