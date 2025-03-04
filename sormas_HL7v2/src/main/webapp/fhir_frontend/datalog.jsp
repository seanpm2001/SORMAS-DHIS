 <%           
    if(session.getAttribute("xloggedx") != null || session.getAttribute("xloggedx") == "truet_" ){
%>
<%@page import="com.mirabilia.org.hzi.test.tester_"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.parser.JSONParser"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mirabilia.org.hzi.Util.dhis.optionFiler"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.mirabilia.org.hzi.sormas.doa.DbConnector"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <jsp:include page="template/head.jsp"></jsp:include>
        <body class="sidebar-mini layout-fixed layout-navbar-fixed layout-footer-fixed control-sidebar-slide-open accent-purple">
            <link href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css"/>

            <div class="wrapper">


                <!-- Navbar -->
            <jsp:include page="template/nav.jsp"></jsp:include>
                <!-- /.navbar -->

                <!-- Main Sidebar Container -->
            <jsp:include page="template/aside.jsp"></jsp:include> 


                <style>
                    .switch,.switch * {
                        -webkit-tap-highlight-color: transparent;
                        -webkit-user-select: none;
                        -moz-user-select: none;
                        -ms-user-select: none;
                        user-select: none
                    }

                    .switch label {
                        /* cursor:pointer; */
                    }

                    .switch label input[type=checkbox] {
                        opacity: 0;
                        width: 0;
                        height: 0
                    }

                    .switch label input[type=checkbox]:checked+.lever {
                        background-color: #84c7c1
                    }

                    .switch label input[type=checkbox]:checked+.lever:before,.switch label input[type=checkbox]:checked+.lever:after {
                        left: 18px
                    }

                    .switch label input[type=checkbox]:checked+.lever:after {
                        background-color: #26a69a
                    }

                    .switch label .lever {
                        /* content:""; */
                        display: inline-block;
                        position: relative;
                        width: 36px;
                        height: 14px;
                        background-color: rgba(0,0,0,0.38);
                        border-radius: 15px;
                        margin-right: 10px;
                        -webkit-transition: background 0.3s ease;
                        transition: background 0.3s ease;
                        vertical-align: middle;
                        margin: 0 16px;
                    }

                    .switch label .lever:before,.switch label .lever:after {
                        content: "";
                        position: absolute;
                        display: inline-block;
                        width: 20px;
                        height: 20px;
                        border-radius: 50%;
                        left: 0;
                        top: -3px;
                        -webkit-transition: left 0.3s ease, background .3s ease, -webkit-box-shadow 0.1s ease, -webkit-transform .1s ease;
                        transition: left 0.3s ease, background .3s ease, -webkit-box-shadow 0.1s ease, -webkit-transform .1s ease;
                        transition: left 0.3s ease, background .3s ease, box-shadow 0.1s ease, transform .1s ease;
                        transition: left 0.3s ease, background .3s ease, box-shadow 0.1s ease, transform .1s ease, -webkit-box-shadow 0.1s ease, -webkit-transform .1s ease
                    }

                    .switch label .lever:before {
                        background-color: rgba(38,166,154,0.15)
                    }

                    .switch label .lever:after {
                        background-color: #F1F1F1;
                        -webkit-box-shadow: 0px 3px 1px -2px rgba(0,0,0,0.2),0px 2px 2px 0px rgba(0,0,0,0.14),0px 1px 5px 0px rgba(0,0,0,0.12);
                        box-shadow: 0px 3px 1px -2px rgba(0,0,0,0.2),0px 2px 2px 0px rgba(0,0,0,0.14),0px 1px 5px 0px rgba(0,0,0,0.12)
                    }

                    input[type=checkbox]:checked:not(:disabled) ~ .lever:active::before,input[type=checkbox]:checked:not(:disabled).tabbed:focus ~ .lever::before {
                        -webkit-transform: scale(2.4);
                        transform: scale(2.4);
                        background-color: rgba(38,166,154,0.15)
                    }

                    input[type=checkbox]:not(:disabled) ~ .lever:active:before,input[type=checkbox]:not(:disabled).tabbed:focus ~ .lever::before {
                        -webkit-transform: scale(2.4);
                        transform: scale(2.4);
                        background-color: rgba(0,0,0,0.08)
                    }

                    .switch input[type=checkbox][disabled]+.lever {
                        cursor: default;
                        background-color: rgba(0,0,0,0.12)
                    }

                    .switch label input[type=checkbox][disabled]+.lever:after,.switch label input[type=checkbox][disabled]:checked+.lever:after {
                        background-color: #949494
                    }

                    .disabledbutton {
                        pointer-events: none;
                        opacity: 0.4;
                    }

                </style>
                <style>
                    #overlay {
                        position: fixed;
                        display: none;
                        width: 100%;
                        height: 100%;
                        top: 0;
                        left: 0;
                        right: 0;
                        bottom: 0;
                        background-color: rgba(0,0,0,0.5);
                        z-index: 2;
                        cursor: pointer;
                    }

                    #text{
                        position: absolute;
                        top: 50%;
                        left: 50%;
                        font-size: 50px;
                        color: white;
                        transform: translate(-50%,-50%);
                        -ms-transform: translate(-50%,-50%);
                    }
                </style>

                <!-- Content Wrapper. Contains page content -->
                <div class="content-wrapper">
                    <!-- Content Header (Page header) -->
                    <section class="content-header">
                        <div class="container-fluid">
                            <div class="row mb-2">
                                <div class="col-sm-6">
                                    <h2>Operation Logs</h2>
                                </div>
                                <div class="col-sm-6">
                                    <ol class="breadcrumb float-sm-right">
                                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                                        <li class="breadcrumb-item active">Logs</li>
                                    </ol>
                                </div>
                            </div>
                        </div><!-- /.container-fluid -->
                    </section>


                    <section class="col-lg-12 connectedSortable">
                        <div class="row">
                            <!-- Source creator -->
                            <section class="col-lg-12 connectedSortable">
                                <!-- TO DO List -->
                                <div class="card card-default">
                                    <div class="card-header">
                                        <h3 class="card-title">
                                            <i class="fas fa-history"></i>
                                            Log Review
                                        </h3>
                                    </div>
                                    <div class="card-body">
                                        <div class="callout" id="stepx">
                                            <h5>Operation Log</h5>

                                            <p>Please click on the body to get the full details of the response body.</p>


                                            <table id="oplog" class="display col-lg-12 col-md-12" style="table-layout: fixed">
                                                <thead>
                                                    <tr>
                                                        <th>Body</th>
                                                        <th>Status</th>
                                                        <th>Created</th>
                                                        <th>Data Source</th>
                                                        <th>Data Period</th>
                                                        <th>Case Detail</th>
                                                        <th>Last Updated</th>
                                                    </tr>
                                                </thead>
                                                <tfoot>
                                                    <tr>
                                                        <th>Body</th>
                                                        <th>Status</th>
                                                        <th>Created</th>
                                                        <th>Data Source</th>
                                                        <th>Data Period</th>
                                                        <th>Case Detail</th>
                                                        <th>Last Updated</th>
                                                    </tr>
                                                </tfoot>
                                            </table>



                                        </div>
                                    </div>

                                </div> 
                            </section> 

                        </div>             

                    </section>




                </div>



                <!-- Modal -->
                <div class="modal fade" id="RespondX" role="dialog">
                    <div class="modal-dialog">

                        <!-- Modal content-->
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>

                            </div>
                            <div class="modal-body" style="overflow-x: auto;">
                                <p id="xdb"></p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            </div>
                        </div>

                    </div>
                </div>


                <div id="overlay" onclick="off()">
                    <div id="text"><h2>please wait...</h2>
                    </div>
                </div>

            </div>
        <jsp:include page="template/scripts_footer.jsp"></jsp:include>
        <script src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>


        <script>

                    $(document).ready(function () {

                        $('#oplog').DataTable({
                                 "ajax": '../OperationLogJson?tran=2',
                            bAutoWidth: false,
                            aoColumns: [
                                {sWidth: '50%', BVisible: false, class: 'clickable'},
                                {sWidth: '5%', },
                                {sWidth: '5%'},
                                {sWidth: '5%'},
                                {sWidth: '5%'},
                                {sWidth: '5%'},
                                {sWidth: '5%'}
                            ]
                        });
                    });




                    function loadDoc() {
                        var xhttp = new XMLHttpRequest();
                        xhttp.onreadystatechange = function () {
                            if (this.readyState == 4 && this.status == 200) {
                                document.getElementById("demo").innerHTML =
                                       LoadJson(this.responseText);
                            }
                        };
                        xhttp.open("GET", "../OperationLogJson?tran=2", true);
                        xhttp.send();
                    };






                      $(document).click(function (event) {
                     var text = $(event.target).text();
                     var status = $(event.target).attr('class');
                     
                      
                       if (status === "clickable sorting_1" || status === "clickable"){
                          //  alert(status);
                            LoadJson(text);
                       }
                   });

                    function LoadJson(text) {

                        text = text.replaceAll("\'", "\"");
                        var jsonObj;

                        var setJSON = function () {
                            try {
                                jsonObj = JSON.parse(text);
                            } catch (err) {
                                alert(err);
                            }
                        };
                        
                        var jsonViewer = new JSONViewer();

                        document.querySelector("#xdb").appendChild(jsonViewer.getContainer());

                        setJSON();


                        jsonViewer.showJSON(jsonObj, null, 1);
                        json = "";
                        
                        $('#RespondX').modal('show');



                    }
                    ;




        </script>
    </body>
</html>



<%
    } else {
    response.sendRedirect(request.getContextPath());
    }

%>