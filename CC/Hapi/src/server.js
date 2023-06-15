const Hapi = require('@hapi/hapi');
const routes = require('./routes');
const db = require('./db_config');

const init = async () => {
  const server = Hapi.server({
    port: 8080,
    host: 'localhost',
  });

  server.route(routes);

  await server.start();
  console.log(`Server berjalan pada ${server.info.uri}`);

  /* Connect to database */
  db.connect((err) => {
    if (err) throw err;
    console.log('Connected!');
  });
};

init();
