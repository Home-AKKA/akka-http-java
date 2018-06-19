package com.example.akkahttp.web.route;

import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.Uri;
import akka.http.javadsl.model.headers.Location;
import akka.http.javadsl.server.HttpApp;
import akka.http.javadsl.server.Route;
import akka.http.javadsl.server.RouteResult;
import akka.http.javadsl.server.values.PathMatcher;
import com.example.akkahttp.dao.GroupRepository;
import com.example.akkahttp.model.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.inject.Inject;
import java.util.*;
import static akka.http.javadsl.marshallers.jackson.Jackson.jsonAs;
import static akka.http.javadsl.model.HttpResponse.create;
import static akka.http.javadsl.server.RequestVals.entityAs;
import static akka.http.javadsl.server.values.PathMatchers.uuid;
import static akka.http.scaladsl.model.StatusCodes.*;


public class GroupRoute extends HttpApp {

    private static final Logger log = LoggerFactory.getLogger(GroupRoute.class);

    private final GroupRepository groupRepository;

    @Inject
    public GroupRoute(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Route createRoute() {
        // Тот же самый ?орган? должен использоваться для запроса PathMatcher!
        PathMatcher<UUID> uuidExtractor = uuid();

        return handleExceptions(e -> {
                  e.printStackTrace();
                  return complete(create().withStatus(InternalServerError()));
              },
              pathSingleSlash().route(
                      getFromResource("web/index.html") ),
              pathPrefix("groups").route(
                      get(pathEndOrSingleSlash().route(
                              handleWith(ctx -> {
                                  log.debug("get http://localhost:8080/groups");
                                  Collection<Group> groups = groupRepository.find();
                                  RouteResult result = ctx.completeAs(Jackson.json(), groups);
                                  return result;
                              }) )),

                          get(path(uuidExtractor).route(
                                handleWith(uuidExtractor,
                                        (ctx, uuid) -> {
                                            log.debug("get http://localhost:8080/groups/{}", uuid);
                                            Group group = groupRepository.findOne(uuid);
                                            RouteResult result = ctx.completeAs(Jackson.json(), group);
                                            return result;
                                }) )),

                          post(handleWith(entityAs(jsonAs(Group.class)),
                                (ctx, group) -> {
                                    log.debug("post http://localhost:8080/groups");
                                    Group newGroup = groupRepository.save(group);
                                    RouteResult result = ctx.complete(HttpResponse.create()
                                            .withStatus(Created())
                                            .addHeader(Location.create(Uri.create("http://localhost:8080/groups/" + newGroup.getUuid()))));
                                    return result;
                                })),

                          put(path(uuidExtractor).route(
                                  handleWith(uuidExtractor, entityAs(jsonAs(Group.class)),
                                        (ctx, uuid, group) -> {
                                            if (!Objects.equals(group.getUuid(), uuid))
                                                return ctx.completeWithStatus(BadRequest());
                                            else {
                                                groupRepository.update(group);
                                                return ctx.completeWithStatus(OK());
                                            }
                                } ) )),

                          delete(path(uuidExtractor).route(
                                handleWith(uuidExtractor, (ctx, uuid) -> {
                                    log.debug("delete http://localhost:8080/groups/{}", uuid);
                                    groupRepository.delete(uuid);
                                    RouteResult result =  ctx.completeWithStatus(OK());
                                    return result;
                                } ) ))
                      ) );
    }
}
