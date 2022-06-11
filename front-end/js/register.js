window.addEventListener('DOMContentLoaded', async () => {

  const registerForm= document.querySelector("#registerForm")

  const fullName = document.querySelector("#fullName");
  const username = document.querySelector("#username")
  const email = document.querySelector("#email")
  const password = document.querySelector("#password")
  
  registerForm.addEventListener("submit", async (e) => {
      e.preventDefault();
      
      if(!fullName?.value?.trim()?.length >= 3){
        return alert("Full name must be at least 3 characters long");
      }

      if(!username?.value?.trim()?.length >= 3){
        return alert("Username must be at least 3 characters long");
      }

      if(/^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/.test(email?.value?.trim()) === false){
        return alert("Email must be at least 3 characters long");
      }
  
      if(!password?.value?.trim()?.length >= 1){
        return alert("Password must be at least 1 characters long");
      }
  
      try{
        const dataResponse = await fetch(BACKEND_URL + "/api/register", {
          method: "POST",
          headers: {
          "Content-Type": "application/json"          
          },
  
          body: JSON.stringify({
            fullName: fullName.value.trim(),
            email: email.value.trim(),
            username: username.value.trim(),
            password: password.value.trim()
          })
        })
        
        const data = await dataResponse.json();

        if(data.status == 401){
          return alert("User already exists");
        }
      
        console.log(data);
        // window.localStorage.setItem("token", data.access_token)
        window.location.href = "./login.html"
      } catch(e) {
        return alert("User already exists");
      }
  
  })
})