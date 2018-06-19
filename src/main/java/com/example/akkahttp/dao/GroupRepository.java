package com.example.akkahttp.dao;

import com.example.akkahttp.model.Group;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class GroupRepository {

  private final ConcurrentHashMap<UUID, Group> groups = new ConcurrentHashMap<>();

  {
    groups.put(UUID.fromString("d0926864-e5e7-4bca-8067-d05eb7c725e9"), new Group(UUID.fromString("d0926864-e5e7-4bca-8067-d05eb7c725e9"), "BLa bla"));
  }

  public Collection<Group> find() {
    return groups.values();
  }

  public Group findOne(UUID uuid) {
    return groups.get(uuid);
  }

  public Group save(Group group) {
    UUID uuid = UUID.randomUUID();

    Group groupWithId = new Group(uuid, group.getName());
    groups.put(uuid, groupWithId);
    return groupWithId;
  }

  public void update(Group group) {
    groups.put(group.getUuid(), group);
  }

  public void delete(UUID uuid) {
    groups.remove(uuid);
  }
}
