package com.yeonsu.settle.domain.group;

import com.yeonsu.settle.domain.user.Role;
import com.yeonsu.settle.domain.user.User;
import com.yeonsu.settle.domain.user.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;

    @After
    public void cleanup() {
        groupRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    public void 그룹저장_불러오기() {
        // given
        String name = "테스트 그룹";

        User yeonsu = User.builder().name("연수").email("shenlianxiu@gmail.com").role(Role.GUEST).picture("this is not a picture yet").build();
        User yeonwha = User.builder().name("연화").email("shenlianwha@gmail.com").role(Role.GUEST).picture("this is not a picture yet").build();
        User yunseong = User.builder().name("윤성").email("dbstjd2@gmail.com").role(Role.GUEST).picture("this is not a picture yet").build();
        userRepository.save(yeonsu);
        userRepository.save(yeonwha);
        userRepository.save(yunseong);

        Set<User> members = new HashSet<>();
        members.add(yeonsu);
        members.add(yeonwha);
        members.add(yunseong);

        groupRepository.save(
                Group.builder().name("테스트 그룹").members(members).build());

        // when
        List<Group> groupList = groupRepository.findAll();
        Optional<User> ou = userRepository.findByEmail("shenlianxiu@gmail.com");

        // then
        Group group = groupList.get(0);

        assertThat(group.getName()).isEqualTo(name);
        assertThat(group.getMembers().contains(yeonsu)).isEqualTo(true);

        if (ou.isPresent()) {
            assertThat(ou.get().getName()).isEqualTo("연수");
            assertThat(ou.get().getGroups().contains(group)).isEqualTo(true);
        }

    }

    @Test
    public void BaseTimeEntity_등록() {
        // given
        LocalDateTime now = LocalDateTime.of(2024, 8, 29, 0, 0, 0);

        String name = "테스트 그룹";

        User yeonsu = User.builder().name("연수").email("shenlianxiu@gmail.com").role(Role.GUEST).picture("this is not a picture yet").build();
        User yeonwha = User.builder().name("연화").email("shenlianwha@gmail.com").role(Role.GUEST).picture("this is not a picture yet").build();
        User yunseong = User.builder().name("윤성").email("dbstjd2@gmail.com").role(Role.GUEST).picture("this is not a picture yet").build();
        userRepository.save(yeonsu);
        userRepository.save(yeonwha);
        userRepository.save(yunseong);

        Set<User> members = new HashSet<>();
        members.add(yeonsu);
        members.add(yeonwha);
        members.add(yunseong);

        groupRepository.save(
                Group.builder().name("테스트 그룹").members(members).build());

        // when
        List<Group> groupList = groupRepository.findAll();


        // then
        Group group = groupList.get(0);

        System.out.println(">>>>>>>>> createdDate=" + group.getCreatedDate() +
                ", modifiedDate=" + group.getModifiedDate());

        assertThat(group.getCreatedDate()).isAfter(now);
        assertThat(group.getModifiedDate()).isAfter(now);
    }
}
