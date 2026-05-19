const path = require('path');
const fs = require('fs');

// __dirname is the generated webpack config dir: build/js/packages/Portfolio-composeApp/
// ../../../../ is the project root — same pattern used by KotlinJS for its own static entries
const projectRoot = path.resolve(__dirname, '../../../..');
const resumePdf = path.join(projectRoot, 'composeApp/src/commonMain/composeResources/files/resume.pdf');

config.devServer = {
    ...config.devServer,
    historyApiFallback: true,
    setupMiddlewares: function(middlewares, devServer) {
        // Serves the PDF resume at /resume.pdf before historyApiFallback swallows the request.
        middlewares.unshift({
            name: 'resume-pdf',
            middleware: function(req, res, next) {
                if (req.url !== '/resume.pdf') return next();
                res.setHeader('Content-Type', 'application/pdf');
                res.end(fs.readFileSync(resumePdf));
            },
        });
        return middlewares;
    },
};
