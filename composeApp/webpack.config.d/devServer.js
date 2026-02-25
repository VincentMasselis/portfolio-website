const path = require('path');
const fs = require('fs');

// __dirname is the generated webpack config dir: build/js/packages/Portfolio-composeApp/
// ../../../../ is the project root — same pattern used by KotlinJS for its own static entries
const projectRoot = path.resolve(__dirname, '../../../..');
const resumeDir = path.join(projectRoot, 'composeApp/src/commonMain/composeResources/files/resume');

const mimeTypes = {
    '.html': 'text/html',
    '.js': 'application/javascript',
    '.css': 'text/css',
};

config.devServer = {
    ...config.devServer,
    historyApiFallback: true,
    setupMiddlewares: function(middlewares, devServer) {
        // Runs before historyApiFallback so /resume is not swallowed by the SPA fallback.
        // Mirrors the nginx `location /resume` block used in production.
        middlewares.unshift({
            name: 'resume-static',
            middleware: function(req, res, next) {
                if (!req.url.startsWith('/resume')) return next();

                // Redirect /resume → /resume/ so relative URLs in index.html resolve correctly
                if (req.url === '/resume') {
                    res.writeHead(301, { Location: '/resume/' });
                    res.end();
                    return;
                }

                let filePath = req.url.slice('/resume'.length);
                if (!filePath || filePath === '/') filePath = '/index.html';

                const fullPath = path.join(resumeDir, filePath);
                // Prevent path traversal
                if (!fullPath.startsWith(resumeDir)) return next();

                if (fs.existsSync(fullPath) && fs.statSync(fullPath).isFile()) {
                    const ext = path.extname(fullPath);
                    res.setHeader('Content-Type', mimeTypes[ext] || 'application/octet-stream');
                    res.end(fs.readFileSync(fullPath));
                } else {
                    next();
                }
            },
        });
        return middlewares;
    },
};
