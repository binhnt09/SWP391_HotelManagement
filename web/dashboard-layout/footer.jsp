<%-- 
    Document   : footer
    Created on : Jun 7, 2025, 5:49:23 PM
    Author     : viet7
--%>

<!---footer---->
<footer class="footer">
    <div class="container-fluid">
        <div class="footer-in">
            <p class="mb-0">&copy <%= java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) %> Vishweb design - All Rights Reserved.</p>
        </div>
    </div>
</footer>
</div>
</div>

<!----------html code complete----------->

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="js/jquery-3.3.1.slim.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery-3.3.1.min.js"></script>


<script type="text/javascript">

    $(document).ready(function () {
        $(".xp-menubar").on('click', function () {
            $('#sidebar').toggleClass('active');
            $('#content').toggleClass('active');
        });

        $(".xp-menubar,.body-overlay").on('click', function () {
            $('#sidebar,.body-overlay').toggleClass('show-nav');
        });

    });
</script>

</body> 
</html>

