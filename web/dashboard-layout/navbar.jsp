<%-- 
    Document   : navbar
    Created on : Jun 7, 2025, 6:34:29 PM
    Author     : viet7
--%>

<!----------------top-navbar---------------->
<div class="top-navbar">
    <div class="xp-topbar">

        <!-- Start XP Row -->
        <div class="row"> 
            <!-- Start XP Col -->
            <div class="col-2 col-md-1 col-lg-1 order-2 order-md-1 align-self-center">
                <div class="xp-menubar">
                    <span class="material-icons text-white">signal_cellular_alt
                    </span>
                </div>
            </div> 
            <!-- End XP Col -->

            <!-- Start XP Col -->
            <div class="col-md-5 col-lg-3 order-3 order-md-2">
                <div class="xp-searchbar">
                    <form method="get" action="<%= servletPath %>">
                        <div class="input-group">
                            <input type="search" class="form-control" name="keyword"
                                   value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>"
                                   placeholder="Search">
                            <div class="input-group-append">
                                <button class="btn" type="submit" id="button-addon2">GO</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <!-- End XP Col -->

            <!-- Start XP Col -->
            <div class="col-10 col-md-6 col-lg-8 order-1 order-md-3">
                <div class="xp-profilebar text-right">
                    <nav class="navbar p-0">
                        <ul class="nav navbar-nav flex-row ml-auto">   
                            <li class="dropdown nav-item active">
                                <a href="#" class="nav-link" data-toggle="dropdown">
                                    <span class="material-icons">notifications</span>
                                    <span class="notification">4</span>
                                </a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a href="#">You have 5 new messages</a>
                                    </li>
                                    <li>
                                        <a href="#">You're now friend with Mike</a>
                                    </li>
                                    <li>
                                        <a href="#">Wish Mary on her birthday!</a>
                                    </li>
                                    <li>
                                        <a href="#">5 warnings in Server Console</a>
                                    </li>

                                </ul>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">
                                    <span class="material-icons">question_answer</span>

                                </a>
                            </li>
                            <li class="nav-item dropdown">
                                <a class="nav-link" href="#" data-toggle="dropdown">
                                    <img src="img/user.jpg" style="width:40px; border-radius:50%;"/>
                                    <span class="xp-user-live"></span>
                                </a>
                                <ul class="dropdown-menu small-menu">
                                    <li>
                                        <a href="#">
                                            <span class="material-icons">
                                                person_outline
                                            </span>Profile

                                        </a>
                                    </li>
                                    <li>
                                        <a href="#"><span class="material-icons">
                                                settings
                                            </span>Settings</a>
                                    </li>
                                    <li>
                                        <a href="#"><span class="material-icons">
                                                logout</span>Logout</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>


                    </nav>

                </div>
            </div>
            <!-- End XP Col -->

        </div> 
        <!-- End XP Row -->

    </div>
    <div class="xp-breadcrumbbar text-center">
        <h4 class="page-title">Dashboard</h4>  
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="#">Dashboard</a></li>
            <li class="breadcrumb-item active" aria-current="page">Dashboard</li>
        </ol>                
    </div>

</div>
