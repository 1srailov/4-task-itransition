window.addEventListener('DOMContentLoaded', async () => {
  const loginForm = document.querySelector("#loginForm");
  const username = document.querySelector("#username")
  const password = document.querySelector("#password")
  
  loginForm.addEventListener("submit", async (e) => {
      e.preventDefault();
      
      if(!username?.value?.trim()?.length >= 3){
        return alert("Username must be at least 3 characters long");
      }
  
      if(!username?.value?.trim()?.length >= 1){
        return alert("Password must be at least 1 characters long");
      }
  
      try{
        const dataResponse = await fetch(BACKEND_URL + "/api/login", {
          method: "POST",
          headers: {
          "Content-Type": "application/json"          
          },
  
          body: JSON.stringify({
            username: username.value.trim(),
            password: password.value.trim()
          })
        })
        
        const data = await dataResponse.json();
        
        if(data) {
          window.localStorage.setItem("token", data.access_token)
          window.location.href = "./index.html"
        }
      } catch(e) {
        return alert("Invalid username or password");
      }
  })
})