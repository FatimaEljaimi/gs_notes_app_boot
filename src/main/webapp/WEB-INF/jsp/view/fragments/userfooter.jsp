<div class="col-6" style="margin-top:100px">
<p>T. BOUDAA, G�nie Informatique 2</p>
</div>
</div>
<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
<script>
    document.getElementById("form").addEventListener("change",() =>{
        if(document.getElementById("session").value !== "0" && document.getElementById("select").value !== "0"){
            document.getElementById("submit").disabled = false;
        }else
        {
            document.getElementById("submit").disabled = true;

        }
    })
</script>
</body>
</html>