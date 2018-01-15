<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>File Upload</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <form method="POST" action="upload" enctype="multipart/form-data" >
            <table>
                <tr>
                    File:<input type="file" name="file" id="file" /><br />
                </tr>
                <tr>
                    Destination:<input type="text" name="destination"/><br />
                </tr>
                <tr>
                    <input type="submit" value="Upload" name="upload" id="upload" />
                </tr>
            </table>
        </form>
        <a href="export">Export</a>
    </body>
</html>