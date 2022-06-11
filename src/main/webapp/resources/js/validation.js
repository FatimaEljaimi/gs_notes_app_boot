
document.getElementById("form").addEventListener("change",() =>{
    if(document.getElementById("session").value !== 0 && document.getElementById("select").value !== 0){
        document.getElementById("submit").disabled = false;
    }
})
