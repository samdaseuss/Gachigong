import com.example.studytime.dto;

import com.example.studytime.entity.GroupEntity;
import com.example.studytime.entity.GroupMemberEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GroupDto {
    private long id;
    private String groupName;
    private String groupIntro;
    private MemberEntity groupOwner;
    private List<GroupMemberEntity> groupMember;

    public static GroupDto toGroupDto(GroupEntity groupEntity){
        GroupDto groupDto = new GroupDto();
        groupDto.setId(groupEntity.getId());
        groupDto.setGroupName(groupEntity.getGroupName());
        groupDto.setGroupIntro(groupEntity.getGroupIntro());
        groupDto.setGroupOwner(groupEntity.getGroupOwner());
        groupDto.setGroupMember(groupEntity.getGroupMember());
        return groupDto;
    }
}
