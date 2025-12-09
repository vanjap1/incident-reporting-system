FROM node:18-alpine

WORKDIR /opt/frontend-app

COPY package.json package-lock.json ./
RUN npm ci

COPY . .

ARG DEFAULT_PORT=3000
ENV PORT=$DEFAULT_PORT
EXPOSE $PORT

CMD ["npm", "start"]
