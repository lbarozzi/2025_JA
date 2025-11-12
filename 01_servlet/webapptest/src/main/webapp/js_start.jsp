<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JavaScript Start Page</title>
        <style>
            body { font-family: Arial, sans-serif; margin: 40px; }
            h1 { color: #333; }
            #message { display: block; margin-top: 20px; padding: 10px; border: 1px solid black; }
        </style>
    </head>
    <body>
        <h1>Welcome to the JavaScript Start Page</h1>
        <p>This is a simple JSP page to demonstrate JavaScript integration.</p>
        <p>Page visits: <span id="visitCount">0</span></p>
        <span id="message" >&nbsp;</span>
        <input type="text" id="inputField" placeholder="to do something...">
        <br>
        <button onclick="addToDo()">Add to To-Do List</button>
        <button onclick="clearToDoList()">Clear To-Do List</button>
        <ol id="todolist"></ol>
        <script>
            document.getElementById("message").innerHTML =
                '<p id="p1">E... muti! <strong>JavaScript is working!</strong></p>';
            let count=0;

            fetch("http://localhost:8080/webapptest/api/todos")
                .then(response => response.json())
                .then(data => {
                    const todoList = document.getElementById("todolist");
                    // Verifica che la risposta sia corretta
                    if (data.success && data.todolist) {
                        data.todolist.forEach(item => {
                            const li = document.createElement("li");
                            const text = item.title + (item.description ? " - " + item.description : "");
                            li.textContent = text;
                            if (item.completed) {
                                li.style.textDecoration = "line-through"; li.style.color = "#888";
                            }
                            todoList.appendChild(li);
                        });
                    } else { console.error('Invalid response format:', data); }
                })
                .catch(error => {
                    console.error('Error fetching to-do items:', error);
                    document.getElementById("message").innerHTML = 
                        '<p style="color:red">Error fetching to-do items: ' + error.message + '</p>';
                });

            function aggiornaContatore() {
                count++;
                document.getElementById("visitCount").innerText = count;
            }
            function addToDo(){
                const tdi = document.getElementById("inputField").value;
                if(tdi.trim()==="") return;
                const nli = document.createElement("li");
                const btn = document.createElement("button");
                nli.innerText=tdi;
                btn.innerText="X";
                btn.onclick=function(){
                    document.getElementById("todolist").removeChild(nli);
                    //oppure
                    //nli.remove();
                };
                
                document.getElementById("todolist").appendChild(nli);
                nli.appendChild(btn);
                document.getElementById("inputField").value="";
            }
            function clearToDoList(){
                document.getElementById("todolist").innerHTML="";
            }
        </script>
        <button onclick="aggiornaContatore()">Click me to increase visit count</button>


    </body>
</html>