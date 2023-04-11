FROM node:14.17.6 AS build
WORKDIR /app
COPY package*.json ./
RUN npm install --legacy-peer-deps
COPY . .
RUN npm run build --prod
#stage-2
FROM nginx:1.21.3-alpine
COPY --from=build /app/dist/lendinglane /usr/share/nginx/html
EXPOSE 80
