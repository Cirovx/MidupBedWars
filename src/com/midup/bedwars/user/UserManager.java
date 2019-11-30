package com.midup.bedwars.user;

import com.midup.bedwars.Main;
import com.midup.bedwars.team.TeamType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UserManager {

    private HashMap<UUID, User> users = new HashMap<>();

    /**
     * Adiciona um usuario ao cache de usuarios
     *
     * @param uuid A uuid do usuario
     */
    public void registerUser(UUID uuid) {
        users.put(uuid, new User(uuid));
    }

    /**
     * Remove um usuario do cache de usuarios
     *
     * @param uuid A uuid do usuário
     */
    public void unregisterUser(User user) {
        Main.getTeamManager().getTeam(user.getTeam().getType()).remUser(user);
        users.remove(user.getUuid());
    }

    /**
     *
     * @param uuid A uuid do usuário
     *
     * @return O usuário registrado
     */
    public User getUser(UUID uuid) {
        return this.users.get(uuid);
    }

    /**
     *
     * @return Todos os usuarios registrado
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        for (User user : this.users.values()) {
            users.add(user);
        }
        return users;
    }

    /**
     *
     * @return Todos os usuarios registrado online
     */
    public List<User> getAllOnlineUsers() {
        List<User> users = new ArrayList<>();
        for (User user : this.users.values()) {
            if (user.isOnline()) {
                users.add(user);
            }
        }
        return users;
    }

    /**
     *
     * @param team A team para buscar usuarios
     * @return A lista de todos os usuarios da team
     */
    public List<User> getTeamUsers(TeamType team) {
        List<User> users = new ArrayList<>();
        for (User user : this.users.values()) {
            if (user.getTeam().getType() == team) {
                users.add(user);
            }
        }
        return users;
    }

    /**
     *
     * @param uuid A uuid do player
     * @return true se ja tiver registrado, false se nao
     */
    public boolean hasRegisteredUser(UUID uuid) {
        return this.users.containsKey(uuid);
    }

}
