<%@include file="../jspf/page.jspf" %>

<fmt:message key="tour-update_image.title" var="title"/>
<c:set var="css" value="${'/css/dropzone.css'}"/>
<t:wrapper title="${title}" css="${css}">
    <script src="/js/dropzone.js"></script>
    <script>
        Dropzone.options.myAwesomeDropzone = { // The camelized version of the ID of the form element

            // The configuration we've talked about above
            autoProcessQueue: false,
            uploadMultiple: false,
            parallelUploads: 100,
            maxFiles: 1,
            acceptedFiles: ".jpg",
            thumbnailHeight: 200,
            thumbnailWidth: 250,
            dictDefaultMessage: "<fmt:message key="dropzone.default"/>",
            dictInvalidFileType: "<fmt:message key="dropzone.invalid_file_type"/>",
            dictResponseError: "<fmt:message key="dropzone.response_error"/>",

            // The setting up of the dropzone
            init: function () {
                var myDropzone = this;

                this.on("maxfilesexceeded", function (file) {
                    this.removeAllFiles();
                    this.addFile(file);
                });


                // Set up any event handlers
                this.on('success', function () {
                    location.reload();
                });

                // First change the button to actually tell Dropzone to process the queue.
                this.element.querySelector("button[type=submit]").addEventListener("click", function (e) {
                    // Make sure that the form isn't actually being sent.
                    e.preventDefault();
                    e.stopPropagation();
                    myDropzone.processQueue();
                });
            }

        }
    </script>
    <style>
        .dropzone .dz-preview .dz-image {
            width: 250px;
            height: 200px;
        }
    </style>
    <div class="container">
        <h4><fmt:message key="tour.image.current"/> </h4>
        <div class="row">
            <div class="col s6">
                <img height="250" src="/images/tour_${param.id}.jpg" class="center-block"/>
            </div>
            <div class="col s6">
                <form action="/images/upload_tour_image?id=${param.id}" class="dropzone" id="my-awesome-dropzone">
                    <div class="dropzone-previews"></div>
                    <button type="submit" class="btn waves-effect">
                        <fmt:message key="tour-edit.button.save"/>
                    </button>
                </form>
            </div>
        </div>
    </div>
</t:wrapper>
