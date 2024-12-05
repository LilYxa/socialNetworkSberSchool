package ru.sberSchool.socialNetwork.friendService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipInvite {
    private UUID userId;
    private UUID friendId;
}
