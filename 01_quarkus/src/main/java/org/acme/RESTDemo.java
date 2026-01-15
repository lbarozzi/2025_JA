package org.acme;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/restdemo")
public class RESTDemo {

    public static class UserDTO {
        @NotBlank(message = "Name is required and cannot be empty")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        private String name;
        
        @NotNull(message = "Age is required")
        @Min(value = 0, message = "Age must be non-negative")
        @Max(value = 150, message = "Age must be realistic (max 150)")
        private Integer age;
    
        public UserDTO() {}
        
        public UserDTO(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { 
            return name; 
        }
        
        public void setName(String name) { 
            this.name = name; 
        }
        
        public Integer getAge() { 
            return age; 
        }
        
        public void setAge(Integer age) { 
            this.age = age; 
        }
        @Override
        public String toString() {
            return String.format("UserDTO{name='%s', age=%d}", name, age); 
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@Valid UserDTO userDTO) {
        System.out.println("Received user: " + userDTO.toString()   );
        return Response
            .status(Response.Status.CREATED)
            .entity(userDTO)
            .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserDTO demo() {
        return  new UserDTO("Leonardo", 54);
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String demoWithPathParam(@PathParam("id") Integer id) {
        if (id == null || id <= 0) {
            throw new WebApplicationException(
                "Invalid ID: must be a positive integer", 
                Response.Status.BAD_REQUEST
            );
        }
        
        return "This is a REST endpoint with a path parameter in Quarkus for id: " + id;
    }
}
