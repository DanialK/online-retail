FROM node:10.15.3-jessie

ENV NODE_ENV=production

RUN mkdir /app
WORKDIR /app

COPY package.json package-lock.json ./

COPY . .

RUN npm install --production

RUN npm run build

EXPOSE 8080

CMD ["npm", "run", "serve"]