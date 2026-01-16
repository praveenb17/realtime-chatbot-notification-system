\# Tech Decisions (Initial)



\## Backend

\- Java + Spring Boot (primary stack)



\## Real-time Transport

\- WebSocket (Spring WebSocket)



\## Database (MVP)

\- PostgreSQL

Reason: simplest reliable choice for relational message storage + indexing + pagination.



\## Async Messaging (Phase 2)

\- Kafka (or JMS) for notification/event pipeline

Decision will be finalized after MVP chat flow is stable.



\## Deployment (Local)

\- Docker Compose (later)

Goal: one-command local setup for reviewers/interviewers.



