const tbodyList = document.querySelector("#tbody")
window.addEventListener('DOMContentLoaded', async () => {
    const token = window.localStorage.getItem("token");
    const usersData = await fetch(BACKEND_URL + "/api/users", {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    if(!usersData?.ok){
        window.localStorage.removeItem("token");
        window.location.href = "./login.html";
    }

    const users = await usersData.json()

    function renderUsers(users){
        if(!users){
            return alert("No users found")
        }

        tbodyList.innerHTML = ""

        for(const user of users){
            const htmlFile = 
            `
                <td><span>${user?.id}</span></td>
                <td><span>${user?.fullName}</span></td>
                <td><span>${user?.username}</span></td>
                <td><span>${user?.email}</span></td>
                <td><span>${user?.createDate}</span></td>
                <td><span>${user?.lastBeenDate}</span></span></td>
                <td><span>${user?.isBlocked}</span></td>
                <td>
                    <label class="control control--checkbox">
                        <input type="checkbox" id="checker" data-userId="${user.id}" class="js-check-all"/>
                        <div class="control__indicator"></div>
                    </label>
                </td>
            `
            tbodyList.innerHTML += htmlFile
        }
    }

    renderUsers(users)

    function deleteUser(){
        const deleteBtn = document.querySelector("#deleteBtn")
        deleteBtn.addEventListener("click", async () => {
            const checkers = document.querySelectorAll("#checker")
            const checkedUsers = []
            for(const checkbox of checkers){
                const checkboxId = checkbox?.dataset.userid
                if(checkbox?.checked){
                        checkedUsers.push(checkboxId)
                }
            }    
            const deleteUserData = await fetch(BACKEND_URL + "/api/edit/delete", {
                method: "DELETE",
                headers: {
                    "Authorization": "Bearer " + token,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(checkedUsers)
            })
            if(!deleteUserData.ok){
                return alert("Users not delete!")
            }else{
                window.location.href = "./index.html"
            }
        })
    }

    function blockUser(){
        const blockBtn = document.querySelector("#blockBtn")
        blockBtn.addEventListener("click", async () => {
            const checkers = document.querySelectorAll("#checker")
            const checkedUsers = []
            for(const checkbox of checkers){
                const checkboxId = checkbox?.dataset.userid
                if(checkbox?.checked){
                        checkedUsers.push(checkboxId)
                }
            }
            const deleteUserData = await fetch(BACKEND_URL + "/api/edit/block", {
                method: "PUT",
                headers: {
                    "Authorization": "Bearer " + token,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(checkedUsers)
            })
            if(!deleteUserData.ok){
                return alert("Users not block!")
            }else{
                window.location.href = "./index.html"
            }       
        })
    }

    function unblockUser(){
        const unblockBtn = document.querySelector("#unblockBtn")
        unblockBtn.addEventListener("click", async () => {
            const checkers = document.querySelectorAll("#checker")
            const checkedUsers = []
            for(const checkbox of checkers){
                const checkboxId = checkbox?.dataset.userid
                if(checkbox?.checked){
                        checkedUsers.push(checkboxId)
                }
            }      

        const deleteUserData = await fetch(BACKEND_URL + "/api/edit/unblock", {
            method: "PUT",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(checkedUsers)
        })
        if(!deleteUserData.ok){
            return alert("Users not unblock!")
        }else{
            window.location.href = "./index.html"
        }
    })
    }

    blockUser()
    deleteUser()
    unblockUser()
})