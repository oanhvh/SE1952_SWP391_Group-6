<%@ page import="java.util.List" %>
<%@ page import="entity.Skills" %>
<%
    List<Skills> allSkills = (List<Skills>) request.getAttribute("allSkills");
    List<Skills> userSkills = (List<Skills>) request.getAttribute("userSkills");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Skills</title>
    <style>
        body {
            font-family: "Poppins", sans-serif;
            background-color: #f5f7fb;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }

        .container {
            background-color: #fff;
            width: 650px;
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        h2 {
            color: #0c2a4d;
            font-weight: 700;
            margin-bottom: 25px;
        }

        .skills-list {
            text-align: left;
            margin: 25px 0;
            max-height: 350px;
            overflow-y: auto;
            padding-right: 5px;
        }

        .skill-item {
            background-color: #f9fafc;
            border: 1px solid #e2e6ef;
            border-radius: 10px;
            padding: 10px 15px;
            margin-bottom: 10px;
            display: flex;
            flex-direction: column;
            transition: 0.2s;
        }

        .skill-item:hover {
            background-color: #eef3fb;
        }

        .skill-header {
            display: flex;
            align-items: center;
        }

        .skill-item input[type="checkbox"] {
            accent-color: #ff5c33;
            transform: scale(1.2);
            margin-right: 10px;
        }

        .skill-name {
            font-weight: 600;
            color: #0c2a4d;
        }

        .skill-desc {
            font-size: 13px;
            color: #666;
            margin-left: 28px;
            margin-top: 3px;
        }

        .buttons {
            display: flex;
            justify-content: center;
            gap: 15px;
            margin-top: 20px;
        }

        button {
            background-color: #ff5c33;
            color: #fff;
            border: none;
            padding: 10px 25px;
            border-radius: 8px;
            font-weight: 600;
            cursor: pointer;
            transition: 0.3s;
        }

        button:hover {
            background-color: #e24c27;
        }

        a.back {
            background-color: #0c2a4d;
            color: #fff;
            text-decoration: none;
            padding: 10px 25px;
            border-radius: 8px;
            font-weight: 600;
            transition: 0.3s;
        }

        a.back:hover {
            background-color: #183d6d;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Edit Your Skills</h2>

        <form action="profile" method="post">
            <input type="hidden" name="action" value="updateSkills">

            <div class="skills-list">
                <% if (allSkills != null) {
                    for (Skills s : allSkills) {
                        boolean checked = false;
                        if (userSkills != null) {
                            for (Skills us : userSkills) {
                                if (us.getSkillID() == s.getSkillID()) {
                                    checked = true;
                                    break;
                                }
                            }
                        }
                %>
                    <div class="skill-item">
                        <div class="skill-header">
                            <input type="checkbox" name="skillIDs" value="<%= s.getSkillID() %>" <%= checked ? "checked" : "" %> >
                            <span class="skill-name"><%= s.getSkillName() %></span>
                        </div>
                        <div class="skill-desc"><%= s.getDescription() %></div>
                    </div>
                <%  } } %>
            </div>

            <div class="buttons">
                <button type="submit">Save Changes</button>
                <a href="profile" class="back">Back to Profile</a>
            </div>
        </form>
    </div>
</body>
</html>
