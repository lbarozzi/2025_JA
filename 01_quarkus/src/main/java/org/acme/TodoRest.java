package org.acme;

import org.acme.entity.ToDoEntity;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoRest {

    // GET all todos
    @GET
    public List<ToDoEntity> getAll() {
        return ToDoEntity.listAll();
    }

    // GET todo by id
    @GET
    @Path("/{id}")
    public ToDoEntity getById(@PathParam("id") Long id) {
        ToDoEntity todo = ToDoEntity.findById(id);
        if (todo == null) {
            throw new WebApplicationException("Todo not found", Response.Status.NOT_FOUND);
        }
        return todo;
    }

    // GET todos by completion status
    @GET
    @Path("/status/{completed}")
    public List<ToDoEntity> getByStatus(@PathParam("completed") boolean completed) {
        return ToDoEntity.findByCompletionStatus(completed);
    }

    // CREATE new todo
    @POST
    @Transactional
    public Response create(ToDoEntity todo) {
        todo.persist();
        return Response.status(Response.Status.CREATED).entity(todo).build();
    }

    // UPDATE existing todo
    @PUT
    @Path("/{id}")
    @Transactional
    public ToDoEntity update(@PathParam("id") Long id, ToDoEntity updatedTodo) {
        ToDoEntity todo = ToDoEntity.findById(id);
        if (todo == null) {
            throw new WebApplicationException("Todo not found", Response.Status.NOT_FOUND);
        }
        
        // Update fields
        todo.title = updatedTodo.title;
        todo.description = updatedTodo.description;
        todo.completed = updatedTodo.completed;
        todo.prePersist(); // Update lastModifiedAt
        
        return todo;
    }

    // DELETE todo
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = ToDoEntity.deleteById(id);
        if (!deleted) {
            throw new WebApplicationException("Todo not found", Response.Status.NOT_FOUND);
        }
        return Response.noContent().build();
    }
}