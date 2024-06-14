describe('Testing not-found component', () => {
    it('should display the not found page', () => {
        cy.visit('/404');
        cy.get('h1').should('contain.text', 'Page not found !');
    });
});