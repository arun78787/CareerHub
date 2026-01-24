// npm i -D cypress
// npx cypress open

describe('CareerHub E2E', () => {
it('login → upload resume → schedule interview', () => {
cy.visit('http://localhost:5173/login');

cy.get('input[placeholder="Email"]').type('user@test.com');
cy.get('input[placeholder="Password"]').type('password');
cy.contains('Login').click();

cy.url().should('include', '/dashboard');

// Upload resume
cy.visit('/resume');
cy.get('input[type=file]').selectFile('cypress/fixtures/sample.pdf');
cy.contains('Upload').click();

// Schedule interview
cy.visit('/interviews');
cy.get('input[placeholder="Candidate ID"]').type('2');
cy.get('input[type=datetime-local]').type('2026-01-30T10:00');
cy.contains('Schedule').click();

cy.contains('SCHEDULED');
});
});
