package ru.job4j.ref;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStore {
    @GuardedBy("this")
    private final Map<Integer, User> map = new ConcurrentHashMap<>();

    public boolean add(final User user) {
        return map.putIfAbsent(user.getId(), user) == null;
    }

    public boolean update(final User user) {
        return map.replace(user.getId(), user) != null;
    }

    public boolean delete(final User user) {
        return map.remove(user.getId(), user);
    }

    public boolean transfer(final int fromId, final int toId, final int amount) {
        boolean result = false;
        User u1 = map.get(fromId);
        User u2 = map.get(toId);
        if (u1 != null && u2 != null && amount > 0 && u1.getAmount() >= amount) {
            u1.setAmount(u1.getAmount() - amount);
            u2.setAmount(u2.getAmount() + amount);
        }
        return result;
    }

    public Map<Integer, User> ass() {
        return map;
    }
}
